<script lang="ts" setup>
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {ref, Ref} from "vue";
import {UserPageReqVO, UserPageRespVO} from "@/api/system/user/types.ts";
import {ElMessageBox, FormInstance} from "element-plus";
import DictTag from "@/components/DictTag/DictTag.vue";
import {deleteUser, page, resetPassword} from "@/api/system/user";
import {checkPermi} from "@/utils/permission.ts";
import UserForm from "@/views/system/user/UserForm.vue";
import AssignUserRoleForm from "@/views/system/user/AssignUserRoleForm.vue";

const formRef = ref()

const total = ref<number>(0);
const currentPage = ref(1);
const pageSize = ref(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const queryParams: Ref<UserPageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    username: undefined,
    type: undefined,
    email: undefined,
    mobile: undefined,
    status: undefined
})

const userFormRef = ref<FormInstance>()
const pageListData = ref<UserPageRespVO[]>([])

const resetForm = (formEl: FormInstance | undefined) => {
    console.log("reset form")
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

watch([currentPage, pageSize], ([newCurrentPage, newPageSize],[oldCurrentPage, oldPageSize]) => {
    queryParams.value.pageNum = newCurrentPage
    queryParams.value.pageSize = newPageSize
    pageList()
})

interface Tree {
    label: string
    value: number
    children?: Tree[]
}

const handleNodeClick = (data: Tree) => {
    queryParams.value.type = undefined
    if (data.value != 0) {
        queryParams.value.type = data.value
    }
    handleQuery()
}

const userTypeTree: Ref<Tree[]> = ref([])
const initUserTypeTree = () => {
    let userType = {value: 0, label: "zack-blog", children: []}
    userType.children = getDictDataByDictType(DICT_TYPE.USER_TYPE)
    console.log("userType => ", userType)
    userTypeTree.value.push(userType)
}

initUserTypeTree()

const defaultProps = {
    children: 'children',
    label: 'label',
    value: 'value'
}

const openForm = (type: string, id?: number) => {
    formRef.value.open(type, id)
}

const deleteUserById = (row) => {
    ElMessageBox.confirm(`确定要删除用户名为[${row.username}]吗？`,
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
        })
        .then(async () => {
            await deleteUser(row.id)
            ElNotification.success("删除成功")
            await pageList()
        }).catch(() => {

    })
}

const handleResetPassword = (row: UserPageRespVO) => {
    ElMessageBox.confirm(`确定要为当前用户[${row.username}]重置密码吗？`,
        "重置密码",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        })
        .then(async () => {
            await resetPassword({userId: row.id})
            ElNotification.success("重置密码成功")
            await pageList()
        }).catch(() => {

    })
}

const assignRoleFormRef = ref()
const assignRole = (row: UserPageRespVO) => {
    assignRoleFormRef.value.open(row)
}

const handleCommand = (command: string, row: UserPageRespVO) => {
    switch (command) {
        case 'deleteUserById':
            deleteUserById(row)
            break
        case 'handleResetPassword':
            handleResetPassword(row)
            break
        case 'assignRole':
            assignRole(row)
            break
        default:
            break
    }
}

pageList()
</script>

<template>
    <div class="user-container">
        <el-row>
            <el-col :xs="24" :sm="8" :md="5" :lg="3" :xl="3">
                <el-tree empty-text="暂无数据" :data="userTypeTree" default-expand-all :expand-on-click-node="false"
                         :props="defaultProps" @node-click="handleNodeClick"/>
            </el-col>
            <el-col :xs="24" :sm="16" :md="19" :lg="20" :xl="20">
                <el-form ref="userFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
                    <el-form-item label="状态" prop="status" style="width: 240px">
                        <el-select
                                placeholder="请选择用户状态"
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
                    <el-form-item label="用户名称" prop="username">
                        <el-input v-model="queryParams.username" placeholder="请输入用户名称" style="width: 240px"/>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="queryParams.email" placeholder="请输入邮箱" style="width: 240px"/>
                    </el-form-item>
                    <el-form-item label="手机号" prop="mobile">
                        <el-input v-model="queryParams.mobile" placeholder="请输入手机号" style="width: 240px"/>
                    </el-form-item>
                    <el-form-item>
                        <el-button v-hasPermission="['system:user:query']" class="button-global"
                                   type="primary" icon="search" @click="handleQuery()">
                            查询
                        </el-button>
                        <el-button @click="resetForm(userFormRef)" icon="refresh">
                            重置
                        </el-button>
                    </el-form-item>
                </el-form>

                <el-row>
                    <el-button class="button-global" v-hasPermission="['system:user:create']"
                               size="small" type="primary" icon="plus" @click="openForm('create')"
                               plain>新增
                    </el-button>
                    <el-button class="button-global" v-hasPermission="['system:user:import']"
                               size="small" type="info" icon="upload">
                        导入
                    </el-button>
                    <el-button class="button-global" v-hasPermission="['system:user:export']"
                               size="small" type="warning" icon="download">
                        导出
                    </el-button>
                </el-row>

                <el-table table-layout="fixed" :data="pageListData" size="small" style="width: 100%">
                    <el-table-column prop="id" label="编号"/>
                    <el-table-column prop="username" label="用户名"/>
                    <el-table-column prop="nickname" label="昵称"/>
                    <el-table-column prop="email" label="用户邮箱"/>
                    <el-table-column prop="mobile" label="手机号码"/>
                    <el-table-column prop="status" label="状态">
                        <template #default="scope">
                            <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="String(scope.row.status)"/>
                        </template>
                    </el-table-column>
                    <el-table-column prop="loginIp" label="最后登录ip"/>
                    <el-table-column prop="loginDate" label="最后登录时间"/>
                    <el-table-column prop="createTime" label="创建时间"/>
                    <el-table-column label="操作" width="160" align="center">

                        <template #default="scope">


                            <el-button size="small" @click="openForm('update', scope.row.id)"
                                       v-hasPermission="['system:user:update']">修改
                            </el-button>

                            <el-dropdown size="small" style="margin-left: 10px"
                                         v-hasPermission="['system:permission:assign-user-role', 'system:user:reset-password', 'system:user:delete']"
                                         @command="(command) => handleCommand(command, scope.row)"
                            >
                                <el-button type="primary" size="small">
                                    更多
                                    <el-icon>
                                        <component is="ArrowDown"></component>
                                    </el-icon>
                                </el-button>
                                <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item command="assignRole" v-if="checkPermi(['system:permission:assign-user-role'])"
                                                          icon="CircleCheck">
                                            分配角色
                                        </el-dropdown-item>
                                        <el-dropdown-item command="handleResetPassword" icon="Key" v-if="checkPermi(['system:user:reset-password'])">
                                            重置密码
                                        </el-dropdown-item>
                                        <el-dropdown-item command="deleteUserById" icon="Delete"
                                                          v-if="checkPermi(['system:user:delete'])">
                                            删除
                                        </el-dropdown-item>
                                    </el-dropdown-menu>
                                </template>
                            </el-dropdown>


                        </template>
                    </el-table-column>
                </el-table>

                <el-pagination background prev-text="上一页" next-text="下一页"
                               :page-sizes="pageSizes" :default-page-size="10"
                               :default-current-page="1"
                               v-model::page-size="pageSize"
                               v-model::current-page="currentPage"
                               layout="total, sizes, prev, pager, next, jumper" :total="total" @sizeChange="sizeChange"
                               @currentChange="currentPageChange"
                               style="display: flex; justify-content: flex-end; margin-top: 15px"

                               small
                />
            </el-col>
        </el-row>

        <UserForm ref="formRef" @success="pageList"/>

        <AssignUserRoleForm ref="assignRoleFormRef" />
    </div>
</template>


<style scoped>

</style>