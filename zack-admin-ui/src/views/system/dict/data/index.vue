<script lang="ts" setup>

import {DICT_TYPE, getDictDataByDictType, removeDictDataCache} from "@/utils/dict.ts";
import {checkPermi} from "@/utils/permission.ts";
import {DictDataPageReqVO, DictDataPageRespVO, DictTypeListVO} from "@/api/system/dict/types.ts";
import {ElMessageBox} from "element-plus";
import {Ref} from "vue";
import {deleteDictDataById, page} from "@/api/system/dict/dict-data.ts";
import {getById, getDictTypeList} from "@/api/system/dict/dict-type.ts";
import {onBeforeRouteUpdate, useRoute} from "vue-router";
import DictDataForm from "@/views/system/dict/data/DictDataForm.vue";

const dictDataFormRef = ref()

const total = ref<number>(0);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const queryParams: Ref<DictDataPageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    dictType: undefined,
    status: undefined
})

const dictDataQueryFormRef = ref()
const pageListData = ref<DictDataPageRespVO[]>([])
const dictTypeList: Ref<DictTypeListVO[]> = ref([])

const resetQueryForm = () => {
    const defaultDictType = queryParams.value.dictType
    dictDataQueryFormRef.value.resetFields()
    queryParams.value.dictType = defaultDictType
    handleQuery()
}
const route = useRoute()
const getDictTypeById = async (dictId: any) => {
    queryParams.value.dictType = undefined
    const res = (await getById(dictId)).data
    queryParams.value.dictType = res.data.type
}
const pageList = async () => {
    const resp = (await page(queryParams.value)).data
    pageListData.value = resp.data.data
    total.value = resp.data.total
}
const handleQuery = () => {
    pageList()
}

const getAllDictTypeList = async () => {
    dictTypeList.value = (await getDictTypeList()).data.data;
}

const init = async () => {
    await getDictTypeById(route.params.dictId)
    await getAllDictTypeList()
    await pageList()
}

const sizeChange = (size: number) => {
    pageSize.value = size;
}

const currentPageChange = (page: number) => {
    currentPage.value = page;
}

watch([currentPage, pageSize], ([newCurrentPage, newPageSize],[oldCurrentPage, oldPageSize]) => {
    queryParams.value.pageNum = newCurrentPage
    queryParams.value.pageSize = newPageSize
    pageList()
})

const openForm = (type: string, dictType?: string, id?: number) => {
    dictDataFormRef.value.open(type, dictType, id)
}

const deleteDictData = (row) => {
    ElMessageBox.confirm(`确定要删除字典标签为[${row.label}]吗？`,
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
        })
        .then(async () => {
            await deleteDictDataById(row.id)
            ElNotification.success("删除成功")
            await pageList()
        }).catch(() => {

    })
}
onBeforeRouteUpdate(async (to, from, next) => {
    if (to.params.dictId != from.params.dictId) {
        await getDictTypeById(to.params.dictId)
        await pageList()
        next()
    }
})
init()
</script>

<template>
    <div class="dict-data-container">
        <el-form ref="dictDataQueryFormRef" :model="queryParams" class="demo-form-inline" :inline="true">


            <el-form-item label="字典类型" prop="dictType">
                <el-select
                        placeholder="请选择字典类型"
                        v-model="queryParams.dictType"
                >
                    <el-option
                            v-for="item in dictTypeList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.type"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="状态" prop="status" style="width: 240px">
                <el-select
                        placeholder="请选择字典类型状态"
                        v-model="queryParams.status"
                >
                    <el-option
                            v-for="item in getDictDataByDictType(DICT_TYPE.COMMON_STATUS)"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button v-hasPermission="['system:dict:query']" class="button-global"
                           type="primary" icon="search" @click="handleQuery">
                    查询
                </el-button>
                <el-button @click="resetQueryForm" icon="refresh">
                    重置
                </el-button>
            </el-form-item>
        </el-form>

        <el-row>
            <el-button class="button-global" v-hasPermission="['system:dict:create']"
                       size="small" type="primary" icon="plus" @click="openForm('create', <string>queryParams.dictType)"
                       plain>新增
            </el-button>
            <el-button class="button-global" v-hasPermission="['system:dict:export']"
                       size="small" type="warning" icon="download">
                导出
            </el-button>
        </el-row>

        <el-table table-layout="auto" :data="pageListData" size="small" style="width: 100%">
            <el-table-column prop="id" label="id"/>
            <el-table-column prop="label" label="字典标签"/>
            <el-table-column label="字典键值" prop="value"/>
            <el-table-column label="字典排序" prop="sort"/>
            <el-table-column prop="status" label="状态">
                <template #default="scope">
                    <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="String(scope.row.status)"/>
                </template>
            </el-table-column>
            <el-table-column label="颜色类型" prop="colorType"/>
            <el-table-column prop="remark" label="备注"/>
            <el-table-column label="操作" width="160" align="center">

                <template #default="scope">


                    <el-button size="small" @click="openForm('update', undefined, scope.row.id)"
                               v-hasPermission="['system:dict:update']">修改
                    </el-button>
                    <el-button @click="deleteDictData(scope.row)" size="small" type="danger" icon="Delete"
                               v-if="checkPermi(['system:dict:delete'])">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination background prev-text="上一页" next-text="下一页"
                       :page-sizes="pageSizes" :default-page-size="1"
                       :default-current-page="10"
                       :page-size="pageSize"
                       :current-page="currentPage"
                       layout="total, sizes, prev, pager, next, jumper" :total="total" @sizeChange="sizeChange"
                       @currentChange="currentPageChange"
                       style="display: flex; justify-content: flex-end; margin-top: 15px"
        />

        <DictDataForm ref="dictDataFormRef" @success="init"/>
    </div>
</template>

<style scoped>

</style>