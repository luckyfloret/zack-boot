<script lang="ts" setup>
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {ref, Ref} from "vue";
import {ElMessageBox, FormInstance} from "element-plus";
import DictTag from "@/components/DictTag/DictTag.vue";
import {checkPermi} from "@/utils/permission.ts";
import {DictTypePageReqVO, DictTypePageRespVO} from "@/api/system/dict/types.ts";
import {deleteDictType, page} from "@/api/system/dict/dict-type.ts";
import DictTypeForm from "@/views/system/dict/DictTypeForm.vue";

const total = ref<number>(0);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const queryParams: Ref<DictTypePageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    name: undefined,
    type: undefined,
    status: undefined
})

const dictTypeQueryFormRef = ref<FormInstance>()
const pageListData = ref<DictTypePageRespVO[]>([])
const dictTypeFormRef = ref()
const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
    handleQuery()
}

const pageList = async () => {
    const resp = (await page(queryParams.value)).data
    pageListData.value = resp.data.data
    total.value = resp.data.total
}
const handleQuery = () => {
    pageList()
}

const sizeChange = (size: number) => {
    pageSize.value = size;
}

const currentPageChange = (page: number) => {
    currentPage.value = page;
}

watch([currentPage, pageSize], ([newCurrentPage, newPageSize], [oldCurrentPage, oldPageSize]) => {
    queryParams.value.pageNum = newCurrentPage
    queryParams.value.pageSize = newPageSize
    pageList()
})

const openForm = (type: string, id?: number) => {
    dictTypeFormRef.value.open(type, id)
}

const deleteUserById = (row) => {
    ElMessageBox.confirm(`确定要删除字典名称为[${row.name}]吗？`,
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
        })
        .then(async () => {
            await deleteDictType(row.id)
            ElNotification.success("删除成功")
            await pageList()
        }).catch(() => {

    })
}

pageList()
</script>

<template>
    <div class="dict-container">
        <el-form ref="dictTypeQueryFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
            <el-form-item label="状态" prop="status" style="width: 240px">
                <el-select
                        placeholder="请选择字典类型状态"
                        v-model="queryParams.status"
                        clearable
                >
                    <el-option
                            v-for="item in getDictDataByDictType(DICT_TYPE.COMMON_STATUS)"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="字典名称" prop="name">
                <el-input v-model="queryParams.name" placeholder="请输入字典名称" style="width: 240px"/>
            </el-form-item>
            <el-form-item label="字典类型" prop="type">
                <el-input v-model="queryParams.type" placeholder="请输入字典类型" style="width: 240px"/>
            </el-form-item>
            <el-form-item>
                <el-button v-hasPermission="['system:dict:query']" class="button-global"
                           type="primary" icon="search" @click="handleQuery()">
                    查询
                </el-button>
                <el-button @click="resetForm(dictTypeQueryFormRef)" icon="refresh">
                    重置
                </el-button>
            </el-form-item>
        </el-form>

        <el-row>
            <el-button class="button-global" v-hasPermission="['system:dict:create']"
                       size="small" type="primary" icon="plus" @click="openForm('create')"
                       plain>新增
            </el-button>
            <el-button class="button-global" v-hasPermission="['system:dict:export']"
                       size="small" type="warning" icon="download">
                导出
            </el-button>
        </el-row>

        <el-table table-layout="fixed" :data="pageListData" size="small" style="width: 100%">
            <el-table-column prop="id" label="编号"/>
            <el-table-column prop="name" label="字典名称"/>
            <el-table-column label="字典类型" :show-overflow-tooltip="true">
                <template #default="scope">
                    <router-link :to="`/system/dict/data/${scope.row.id}`" style="text-decoration: none"
                                 class="link-type">
                        <span>{{ scope.row.type }}</span>
                    </router-link>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="状态">
                <template #default="scope">
                    <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="String(scope.row.status)"/>
                </template>
            </el-table-column>
            <el-table-column prop="remark" label="备注"/>
            <el-table-column prop="createTime" label="创建时间"/>
            <el-table-column label="操作" width="160" align="center">

                <template #default="scope">


                    <el-button size="small" @click="openForm('update', scope.row.id)"
                               v-hasPermission="['system:user:update']">修改
                    </el-button>
                    <el-button @click="deleteUserById(scope.row)" size="small" type="danger" icon="Delete"
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

        <DictTypeForm ref="dictTypeFormRef" @success="pageList"/>

    </div>
</template>


<style scoped>
.link-type:focus {
    color: #337ab7;
}

.link-type:hover {
    color: #409EFF;
}
</style>