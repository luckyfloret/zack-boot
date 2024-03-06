<script lang="ts" setup>
import {ElMessageBox, FormInstance} from "element-plus";
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {deleteRole, page} from "@/api/system/role";
import {RolePageReqVO, RolePageRespVO} from "@/api/system/role/types.ts";
import {ref, Ref} from "vue";
import DictTag from "@/components/DictTag/DictTag.vue";
import RoleForm from "@/views/system/role/RoleForm.vue";
import AssignRoleMenuPermissionForm from "@/views/system/role/AssignRoleMenuPermissionForm.vue";

const total = ref<number>(0);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const formRef = ref()

const queryParams: Ref<RolePageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    name: undefined,
    status: undefined
})
const options = ref([])
const roleFormRef = ref<FormInstance>()


const loading = ref(true)

const rolePageList = ref([])
const emptyText = ref("暂无数据")

const pageList = async () => {
    const response = (await page(queryParams.value)).data
    console.log("role response => ", response.data)
    total.value = response.data.total
    rolePageList.value = response.data.data
    loading.value = false
}


const handleQuery = () => {
    pageList()
}


const resetForm = (formEl: FormInstance | undefined) => {
    console.log("reset form")
    if (!formEl) return
    formEl.resetFields()
    handleQuery()
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

const openForm = (type: string, id?: number) => {
    formRef.value.open(type, id)
}

const deleteRoleById = async (row) => {
    ElMessageBox.confirm(`确定要删除名称为[${row.name}]吗？`,
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        })
        .then(async () => {
            await deleteRole(row.id)
            ElNotification.success("删除成功")
            await pageList()
        }).catch(() => {

    })
}

const assignPermissionFormRef = ref()

const openAssignPermissionForm = (role: any) => {
    assignPermissionFormRef.value.open(role)
}

pageList()


</script>

<template>
    <div class="role-container">
        <el-form ref="roleFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
            <el-form-item label="状态" prop="status" style="width: 240px">
                <el-select
                        placeholder="请选择角色状态"
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
            <el-form-item label="角色名称" prop="name">
                <el-input v-model="queryParams.name" placeholder="请输入角色名称" style="width: 240px"/>
            </el-form-item>
            <el-form-item>
                <el-button v-hasPermission="['system:role:query']" class="button-global"
                           type="primary" icon="search" @click="handleQuery">
                    查询
                </el-button>
                <el-button @click="resetForm(roleFormRef)" icon="refresh">
                    重置
                </el-button>
            </el-form-item>
        </el-form>
        <el-row>
            <el-button class="button-global" @click="openForm('create')" v-hasPermission="['system:role:create']"
                       size="small" type="primary" icon="plus"
                       plain>新增
            </el-button>
            <el-button class="button-global" v-hasPermission="['system:role:export']"
                       size="small" type="warning" icon="download">
                导出
            </el-button>
        </el-row>
        <el-table
                :data="rolePageList"
                table-layout="fixed"
                style="width: 100%"
                v-loading="loading"
                :empty-text="emptyText"
                size="small"
        >
            <el-table-column prop="id" label="id"/>
            <el-table-column prop="name" label="角色名称"/>
            <el-table-column prop="code" label="角色编码"/>
            <el-table-column prop="sort" label="排序"/>
            <el-table-column prop="type" label="角色类型">
                <template #default="scope">
                    <dict-tag :type="DICT_TYPE.SYSTEM_ROLE_TYPE" :value="String(scope.row.type)"/>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="状态">
                <template #default="scope">
                    <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="String(scope.row.status)"/>
                </template>
            </el-table-column>

            <el-table-column prop="createTime" label="创建时间"/>
            <el-table-column label="操作" width="260">
                <template #default="scope">
                    <el-button size="small" icon="edit" @click="openForm('update', scope.row.id)"
                               v-hasPermission="['system:role:update']">修改
                    </el-button>
                    <el-button size="small" type="default" v-hasPermission="['system:permission:assign-role-menu']"
                               @click="openAssignPermissionForm(scope.row)" icon="CircleCheck">菜单权限
                    </el-button>
                    <el-button size="small" type="danger" @click="deleteRoleById(scope.row)"
                               v-hasPermission="['system:role:delete']" icon="delete">删除
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

        <RoleForm ref="formRef" @success="pageList"/>

        <AssignRoleMenuPermissionForm ref="assignPermissionFormRef" @success="pageList"/>
    </div>
</template>


<style scoped>

</style>