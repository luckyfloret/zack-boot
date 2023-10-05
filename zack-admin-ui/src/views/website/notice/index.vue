<script setup lang="ts">

import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {ref, Ref} from "vue";
import {ElMessageBox, FormInstance} from "element-plus";
import DictTag from "@/components/DictTag/DictTag.vue";
import {checkPermi} from "@/utils/permission.ts";
import {NoticePageReqVO, NoticePageRespVO, NoticeUpdateStatusReqVO} from "@/api/website/notice/types.ts";
import {deleteNoticeById, page, updateStatus} from "@/api/website/notice";
import NoticeForm from "@/views/website/notice/NoticeForm.vue";
import {CommonStatus} from "@/constants/CommonConstant.ts";

const total = ref<number>(0);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const queryParams: Ref<NoticePageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    title: undefined,
    type: undefined
})

const noticeQueryFormRef = ref<FormInstance>()
const pageListData = ref<NoticePageRespVO[]>([])
const noticeFormRef = ref()
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
    noticeFormRef.value.open(type, id)
}

const deleteNotice = (row) => {
    ElMessageBox.confirm(`确定要删除标题为[${row.title}]吗？`,
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
        })
        .then(async () => {
            await deleteNoticeById(row.id)
            ElNotification.success("删除成功")
            await pageList()
        }).catch(() => {

    })
}

const updateNoticeStatus = async (row: any, status: number) => {
    const data: NoticeUpdateStatusReqVO = {
        id: row.id,
        type: row.type,
        status
    }
    await updateStatus(data);
    ElNotification.success("更新状态成功")
    await pageList()
}

pageList()
</script>

<template>
    <div class="notice-container">
        <el-form ref="noticeQueryFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
            <el-form-item label="类型" prop="type" style="width: 240px">
                <el-select
                        placeholder="请选择类型"
                        v-model="queryParams.type"
                        clearable
                >
                    <el-option
                            v-for="item in getDictDataByDictType(DICT_TYPE.WEBSITE_NOTICE_TYPE)"
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
                <el-button v-hasPermission="['website:notice:query']" class="button-global"
                           type="primary" icon="search" @click="handleQuery()">
                    查询
                </el-button>
                <el-button @click="resetForm(noticeQueryFormRef)" icon="refresh">
                    重置
                </el-button>
            </el-form-item>
        </el-form>

        <el-row>
            <el-button class="button-global" v-hasPermission="['website:notice:create']"
                       size="small" type="primary" icon="plus" @click="openForm('create')"
                       plain>新增
            </el-button>
            <el-button class="button-global" v-hasPermission="['website:notice:export']"
                       size="small" type="warning" icon="download">
                导出
            </el-button>
        </el-row>

        <el-table table-layout="fixed" :data="pageListData" size="small" style="width: 100%">
            <el-table-column prop="id" label="编号"/>
            <el-table-column prop="title" label="标题"/>

            <el-table-column prop="content" label="内容"/>
            <el-table-column prop="type" label="类型">
                <template #default="scope">
                    <dict-tag :type="DICT_TYPE.WEBSITE_NOTICE_TYPE" :value="String(scope.row.type)"/>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="状态">
                <template #default="scope">
                    <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="String(scope.row.status)"/>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"/>
            <el-table-column label="操作" width="240" align="center">

                <template #default="scope">
                    <el-button size="small" v-if="scope.row.status === 1"
                               @click="updateNoticeStatus(scope.row, CommonStatus.ENABLE)">启用
                    </el-button>
                    <el-button size="small" v-else @click="updateNoticeStatus(scope.row,CommonStatus.DISABLE)">关闭
                    </el-button>
                    <el-button size="small" @click="openForm('update', scope.row.id)"
                               v-hasPermission="['website:notice:update']">修改
                    </el-button>
                    <el-button @click="deleteNotice(scope.row)" size="small" type="danger" icon="Delete"
                               v-if="checkPermi(['website:notice:delete'])">
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

        <NoticeForm ref="noticeFormRef" @success="pageList"/>
    </div>
</template>

<style scoped>

</style>