<script setup lang="ts">
import {checkPermi} from "@/utils/permission.ts";
import {ElMessageBox, FormInstance} from "element-plus";
import {FileConfigPageReqVO, FileConfigPageRespVO} from "@/api/infra/fileconfig/types.ts";
import {deleteFileConfigById, page, testUpload, updateFileConfigMaster} from "@/api/infra/fileconfig";
import {Ref} from "vue";
import DictTag from "@/components/DictTag/DictTag.vue";
import {DICT_TYPE} from "@/utils/dict.ts";
import FileConfigForm from "@/views/infra/fileconfig/FileConfigForm.vue";

const total = ref<number>(0);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const queryParams: Ref<FileConfigPageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    name: undefined,
    email: undefined,
    approvalStatus: undefined
})

const fileConfigQueryFormRef = ref<FormInstance>()
const pageListData = ref<FileConfigPageRespVO[]>([])
const fileConfigFormRef = ref()
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
    fileConfigFormRef.value.open(type, id)
}

const deleteFileConfig = (row) => {
    ElMessageBox.confirm(`确定要删除配置名为[${row.name}]吗？`,
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
        })
        .then(async () => {
            await deleteFileConfigById(row.id)
            ElNotification.success("删除成功")
            await pageList()
        }).catch(() => {

    })
}

const updateMaster = async (id: number) => {
    await updateFileConfigMaster(id)
    await pageList()
    ElNotification.success("更新主配置成功")
}

const testFileConfigUpload = async (id: number) => {
    const res = (await testUpload(id)).data
    ElNotification.success("上传成功，图片地址：" + res.data)
}

pageList()
</script>

<template>
    <div class="file-config-container">
        <el-form ref="fileConfigQueryFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
            <el-form-item label="配置名" prop="name">
                <el-input v-model="queryParams.name" placeholder="请输入配置名" style="width: 240px"/>
            </el-form-item>
            <el-form-item>
                <el-button v-hasPermission="['infra:file-config:query']" class="button-global"
                           type="primary" icon="search" @click="handleQuery()">
                    查询
                </el-button>
                <el-button @click="resetForm(fileConfigQueryFormRef)" icon="refresh">
                    重置
                </el-button>
            </el-form-item>
        </el-form>

        <el-row>
            <el-button class="button-global" v-hasPermission="['infra:file-config:create']"
                       size="small" type="primary" icon="plus" @click="openForm('create')"
                       plain>新增
            </el-button>
            <el-button class="button-global" v-hasPermission="['infra:file-config:export']"
                       size="small" type="warning" icon="download">
                导出
            </el-button>
        </el-row>

        <el-table table-layout="fixed" :data="pageListData" size="small" style="width: 100%">
            <el-table-column prop="id" label="编号"/>
            <el-table-column prop="name" label="名称"/>
            <el-table-column prop="master" label="主配置">
                <template #default="scope">
                    <dict-tag :type="DICT_TYPE.INFRA_FILECONFIG_MASTER" :value="String(scope.row.master)" />
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"/>
            <el-table-column prop="remark" label="备注"/>
            <el-table-column label="操作" width="240" align="center">

                <template #default="scope">
                    <el-button size="small" @click="updateMaster(scope.row.id)" type="primary"
                               :disabled="scope.row.master"
                               v-hasPermission="['infra:file-config:update-master']">主配置
                    </el-button>
                    <el-button size="small" @click="openForm('update', scope.row.id)"
                               v-hasPermission="['infra:file-config:update']">修改
                    </el-button>
                    <el-button size="small" @click="testFileConfigUpload(scope.row.id)" type="success"
                               v-hasPermission="['infra:file-config:test-upload']">测试
                    </el-button>
                    <el-button @click="deleteFileConfig(scope.row)" size="small" type="danger" icon="Delete"
                               v-if="checkPermi(['infra:file-config:delete'])">
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

        <FileConfigForm ref="fileConfigFormRef"/>
    </div>
</template>

<style scoped>

</style>