<script lang="ts" setup>
import {MenuRespVO} from "@/api/system/menu/types.ts";
import {ref, Ref} from "vue";
import {deleteMenuById, menuTree} from "@/api/system/menu";
import {DICT_TYPE, DictData, getDictDataByDictType} from "@/utils/dict.ts";
import {ElMessageBox, FormInstance} from "element-plus";
import DictTag from "@/components/DictTag/DictTag.vue";
import MenuForm from "@/views/system/menu/MenuForm.vue";
import {CACHE_KEY} from "@/hooks/useCache.ts";
import {removeCache} from "@/utils/cache.ts";
import {resetAuthStore, resetUserStore} from "@/utils/store.ts";

const menuTreeTable: Ref<Array<MenuRespVO>> = ref([])
const queryParams = ref({
    status: undefined
})
const options = ref([])
const menuFormRef = ref<FormInstance>()

const loading = ref(true)

const menuTreeList = async () => {
    const res = (await menuTree(queryParams.value)).data
    menuTreeTable.value = res.data
}

const getStatusList = () => {
    const statusList: Array<DictData> = getDictDataByDictType(DICT_TYPE.COMMON_STATUS)
    statusList.forEach(item => {
        const {label, value} = item
        options.value.push({label, value})
    })
    loading.value = false
}
const init = async () => {
    getStatusList()
}
init()
menuTreeList()

const handleQuery = () => {
    menuTreeList()
}

const handleDelete = (row) => {
    console.log("handleDelete row => ", row)
    ElMessageBox.confirm('确定要删除吗？',
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        })
        .then(async () => {
            const result = (await deleteMenuById(row.id)).data
            ElNotification.success(result.message)
            removeCache(CACHE_KEY.MENU_TREE)
            resetAuthStore()
            resetUserStore()
            await menuTreeList()
        }).catch(() => {

    })
}

const resetForm = (formEl: FormInstance | undefined) => {
    console.log("reset form")
    if (!formEl) return
    formEl.resetFields()
    handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number, parentId?: number) => {
    formRef.value.open(type, id, parentId)
}

const handleFormCommitSuccess = () => {
    menuTreeList()
    removeCache(CACHE_KEY.MENU_TREE)
}
</script>

<template>
    <div class="container">
        <el-form ref="menuFormRef" :inline="true"  :model="queryParams" class="demo-form-inline">
            <el-form-item label="状态" prop="status">
                <el-select
                    placeholder="请选择菜单状态"
                    v-model="queryParams.status"
                    clearable
                >
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>

            </el-form-item>
            <el-form-item>
                <el-button v-hasPermission="['system:menu:query']" style="margin-left: 12px" class="button-global" type="primary" icon="search" @click="handleQuery">
                    查询
                </el-button>
                <el-button @click="resetForm(menuFormRef)" icon="refresh">
                    重置
                </el-button>
            </el-form-item>

        </el-form>
        <el-row>
            <el-form-item>
                <el-button @click="openForm('create')" class="button-global" size="small" type="primary" icon="plus" plain>新增</el-button>
            </el-form-item>
        </el-row>
        <el-row>
            <el-col>
                <el-table
                    table-layout="auto"
                    size="small"
                    :data="menuTreeTable"

                    row-key="id"
                    :header-cell-style="{backgroundColor: 'rgb(248, 248, 249)'}"
                    :cell-style="{fontSize: '10px'}"
                    lazy
                    v-loading="loading"
                    :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
                >
                    <el-table-column prop="name" label="菜单名称" />
                    <el-table-column prop="icon" label="图标" />
                    <el-table-column prop="sort" label="排序" />
                    <el-table-column prop="permission" label="权限标识" />
                    <el-table-column prop="component" label="组件路径" />
                    <el-table-column prop="status" label="状态">
                        <template #default="scope">
                            <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="String(scope.row.status)" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" />
                    <el-table-column label="操作" width="240">
                        <template #default="scope">
                            <el-button @click="openForm('create', undefined, scope.row.id)" size="small" type="primary" icon="plus"  v-hasPermission="['system:menu:create']">
                                新增
                            </el-button>
                            <el-button @click="openForm('update', scope.row.id, scope.row.parentId)" size="small" type="default" icon="edit"  v-hasPermission="['system:menu:update']">
                                修改
                            </el-button>
                            <el-button size="small" type="danger" icon="delete" @click="handleDelete(scope.row)" v-hasPermission="['system:menu:delete']">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>

        <!-- 表单弹窗：添加/修改 -->
        <MenuForm ref="formRef" @success="handleFormCommitSuccess" />
    </div>
</template>
<style scoped>
.button-global{
    height: 30px;
    padding: 8px 10px;
}
</style>