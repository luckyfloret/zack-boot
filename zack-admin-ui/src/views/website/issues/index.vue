<script setup lang="ts">
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import DictTag from "@/components/DictTag/DictTag.vue";
import {FormInstance} from "element-plus";
import {IssuesPageReqVO, IssuesPageRespVO} from "@/api/website/issues/types.ts";
import {page, updateStatus} from "@/api/website/issues";
import {Ref} from "vue";
import IssuesDetails from "@/views/website/issues/IssuesDetails.vue";

const total = ref<number>(0);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const queryParams: Ref<IssuesPageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    title: undefined,
    type: undefined,
    status: undefined
})

const issuesQueryFormRef = ref<FormInstance>()
const pageListData = ref<IssuesPageRespVO[]>([])
const issuesDetailsRef = ref()
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

const openDetails = (id: number) => {
    issuesDetailsRef.value.open(id)
}
const updateIssuesStatus = async (id: number) => {
    await updateStatus(id);
    await pageList()
    ElNotification.success("更新状态成功")
}

pageList()
</script>

<template>
    <div class="issues-container">
        <el-form ref="issuesQueryFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
            <el-form-item label="状态" prop="approvalStatus" style="width: 240px">
                <el-select
                        placeholder="请选择状态"
                        v-model="queryParams.status"
                        clearable
                >
                    <el-option
                            v-for="item in getDictDataByDictType(DICT_TYPE.WEBSITE_ISSUES_STATUS)"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="类型" prop="type">
                <el-select
                        placeholder="请选择类型"
                        v-model="queryParams.type"
                        clearable
                >
                    <el-option
                            v-for="item in getDictDataByDictType(DICT_TYPE.WEBSITE_ISSUES_TYPE)"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                    />
                </el-select>

            </el-form-item>
            <el-form-item label="标题" prop="title">
                <el-input v-model="queryParams.title" placeholder="请输入标题" style="width: 240px"/>
            </el-form-item>

            <el-form-item>
                <el-button v-hasPermission="['website:issues:query']" class="button-global"
                           type="primary" icon="search" @click="handleQuery()">
                    查询
                </el-button>
                <el-button @click="resetForm(issuesQueryFormRef)" icon="refresh">
                    重置
                </el-button>
            </el-form-item>
        </el-form>

        <el-row>
            <el-button class="button-global" v-hasPermission="['website:issues:export']"
                       size="small" type="warning" icon="download">
                导出
            </el-button>
        </el-row>

        <el-table table-layout="fixed" :data="pageListData" size="small" style="width: 100%">
            <el-table-column prop="id" label="编号"/>
            <el-table-column prop="username" label="用户名"/>
            <el-table-column prop="title" label="标题"/>

            <el-table-column prop="status" label="状态">
                <template #default="scope">
                    <dict-tag :type="DICT_TYPE.WEBSITE_ISSUES_STATUS" :value="String(scope.row.status)"/>
                </template>
            </el-table-column>
            <el-table-column prop="type" label="类型">
                <template #default="scope">
                    <dict-tag :type="DICT_TYPE.WEBSITE_ISSUES_TYPE" :value="String(scope.row.type)"/>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"/>
            <el-table-column label="操作" width="240" align="center">

                <template #default="scope">
                    <el-button size="small" @click="updateIssuesStatus(scope.row.id)"
                               v-if="scope.row.status === 1"
                               v-hasPermission="['website:issues:update-status']">已解决
                    </el-button>
                    <el-button size="small" @click="openDetails(scope.row.id)"
                               v-hasPermission="['website:issues:query']">详情
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

        <IssuesDetails ref="issuesDetailsRef"/>
    </div>
</template>

<style scoped>

</style>