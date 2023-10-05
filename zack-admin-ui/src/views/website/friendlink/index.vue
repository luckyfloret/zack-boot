<script setup lang="ts">

import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {ref, Ref} from "vue";
import {ElMessageBox, FormInstance} from "element-plus";
import DictTag from "@/components/DictTag/DictTag.vue";
import {checkPermi} from "@/utils/permission.ts";
import {
    FriendLinkApprovalRejectReqVO,
    FriendLinkPageReqVO,
    FriendLinkPageRespVO
} from "@/api/website/friendlink/types.ts";
import {approvalPassFriendLink, approvalRejectFriendLink, deleteFriendLinkById, page} from "@/api/website/friendlink";
import FriendLinkForm from "@/views/website/friendlink/FriendLinkForm.vue";
import {UserPageRespVO} from "@/api/system/user/types.ts";
import {resetPassword} from "@/api/system/user";

const total = ref<number>(0);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const queryParams: Ref<FriendLinkPageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    name: undefined,
    email: undefined,
    approvalStatus: undefined
})

const friendLinkQueryFormRef = ref<FormInstance>()
const pageListData = ref<FriendLinkPageRespVO[]>([])
const friendLinkFormRef = ref()
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
    friendLinkFormRef.value.open(type, id)
}

const deleteFriendLink = (row) => {
    ElMessageBox.confirm(`确定要删除网站名称为[${row.name}]吗？`,
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
        })
        .then(async () => {
            await deleteFriendLinkById(row.id)
            ElNotification.success("删除成功")
            await pageList()
        }).catch(() => {

    })
}

const approvalPass = (row) => {
    ElMessageBox.confirm(`确定要通过当前网站[${row.name}]吗？`,
        "审批通过",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        })
        .then(async () => {
            await approvalPassFriendLink(row.id)
            ElNotification.success("审批通过")
            await pageList()
        }).catch(() => {

    })
}

const approvalReject = async row => {
    const result = await ElMessageBox.prompt(
        '请输入驳回原因',"驳回",
        {
            confirmButtonText: "确认",
            cancelButtonText: "取消",
            draggable: true,
        },
    )
    const data: FriendLinkApprovalRejectReqVO = {
        id: row.id,
        rejectOpinion: result.value
    }
    await approvalRejectFriendLink(data)
    ElNotification.success("驳回成功")
    await pageList()
}

const handleCommand = (command: string, row: UserPageRespVO) => {
    switch (command) {
        case 'approvalPassFriendLink':
            approvalPass(row)
            break
        case 'approvalRejectFriendLink':
            approvalReject(row)
            break
        default:
            break
    }
}

pageList()
</script>

<template>
    <div class="friend-link-container">
        <el-form ref="friendLinkQueryFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
            <el-form-item label="状态" prop="approvalStatus" style="width: 240px">
                <el-select
                        placeholder="请选择友情链接审批状态"
                        v-model="queryParams.approvalStatus"
                        clearable
                >
                    <el-option
                            v-for="item in getDictDataByDictType(DICT_TYPE.WEBSITE_FRIENDLINK_STATUS)"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="网站名称" prop="name">
                <el-input v-model="queryParams.name" placeholder="请输入网站名称" style="width: 240px"/>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="queryParams.email" placeholder="请输入邮箱" style="width: 240px"/>
            </el-form-item>
            <el-form-item>
                <el-button v-hasPermission="['website:friend-link:query']" class="button-global"
                           type="primary" icon="search" @click="handleQuery()">
                    查询
                </el-button>
                <el-button @click="resetForm(friendLinkQueryFormRef)" icon="refresh">
                    重置
                </el-button>
            </el-form-item>
        </el-form>

        <el-row>
            <el-button class="button-global" v-hasPermission="['website:friend-link:create']"
                       size="small" type="primary" icon="plus" @click="openForm('create')"
                       plain>新增
            </el-button>
            <el-button class="button-global" v-hasPermission="['website:friend-link:export']"
                       size="small" type="warning" icon="download">
                导出
            </el-button>
        </el-row>

        <el-table table-layout="fixed" :data="pageListData" size="small" style="width: 100%">
            <el-table-column prop="id" label="编号"/>
            <el-table-column prop="name" label="名称"/>

            <el-table-column prop="approvalStatus" label="审批状态">
                <template #default="scope">
                    <dict-tag :type="DICT_TYPE.WEBSITE_FRIENDLINK_STATUS" :value="String(scope.row.approvalStatus)"/>
                </template>
            </el-table-column>
            <el-table-column prop="avatarUrl" label="网站头像">
                <template #default="scope">
                    <el-avatar
                            :src="scope.row.avatarUrl"
                            alt="图片加载失败"
                    />
                </template>
            </el-table-column>
            <el-table-column prop="sort" label="排序"/>
            <el-table-column prop="websiteUrl" label="网站地址"/>
            <el-table-column prop="description" label="网站描述"/>
            <el-table-column prop="email" label="邮箱"/>
            <el-table-column prop="createTime" label="创建时间"/>
            <el-table-column label="操作" width="240" align="center">

                <template #default="scope">
                    <el-button size="small" @click="openForm('update', scope.row.id)"
                               v-hasPermission="['website:friend-link:update']">修改
                    </el-button>
                    <el-button @click="deleteFriendLink(scope.row)" size="small" type="danger" icon="Delete"
                               v-if="checkPermi(['website:friend-link:delete'])">
                        删除
                    </el-button>
                    <el-dropdown size="small" style="margin-left: 10px"
                                 v-if="scope.row.approvalStatus === 1"
                                 v-hasPermission="['website:friend-link:approval']"
                                 @command="(command) => handleCommand(command, scope.row)"
                    >
                        <el-button type="primary" size="small">
                            审批
                            <el-icon>
                                <component is="ArrowDown"></component>
                            </el-icon>
                        </el-button>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item command="approvalPassFriendLink"
                                                  v-if="checkPermi(['website:friend-link:approval'])"
                                                  icon="CircleCheck">
                                    通过
                                </el-dropdown-item>
                                <el-dropdown-item command="approvalRejectFriendLink" icon="Key"
                                                  v-if="checkPermi(['website:friend-link:approval'])">
                                    驳回
                                </el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>


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

        <FriendLinkForm ref="friendLinkFormRef" @success="pageList"/>
    </div>
</template>

<style scoped>

</style>