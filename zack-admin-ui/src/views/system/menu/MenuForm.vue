<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormInstance} from 'element-plus'
import {ElMessageBox, ElNotification} from "element-plus";
import {CommonStatus, MenuType} from "@/constants/CommonConstant.ts";
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import IconSelector from "@/components/icon/IconSelector.vue";
import {createMenu, getMenuById, menuTree, updateMenu} from "@/api/system/menu";
import {removeCache} from "@/utils/cache.ts";
import {CACHE_KEY} from "@/hooks/useCache.ts";
import {resetAuthStore, resetUserStore} from "@/utils/store.ts";

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formType = ref('')
const formLoading = ref(false)

const emit = defineEmits(['success'])
const handleClose = (done: () => void) => {
    ElMessageBox.confirm('确定要关闭吗？',
        dialogTitle.value,
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        })
        .then(() => {
            done()
        })
        .catch(() => {
            // catch error
        })
}

const menuTreeList = ref([])
const getMenuTree = async () => {
    menuTreeList.value = []
    const res = (await menuTree({status: CommonStatus.ENABLE})).data
    let menu = {id: 0, name: '主类目', children: []}
    menu.children = res.data
    menuTreeList.value.push(menu)
    console.log(menuTreeList.value)
}


const formRef = ref()
const formData = ref({
    id: 0,
    parentId: 0,
    name: '',
    type: MenuType.DIR,
    icon: '',
    sort: 0,
    path: '',
    status: CommonStatus.ENABLE,
    component: '',
    permission: '',
    visible: true,
    keepAlive: true
})
const resetForm = () => {
    formData.value = {
        id: 0,
        parentId: 0,
        name: '',
        type: MenuType.DIR,
        icon: '',
        sort: 0,
        path: '',
        status: CommonStatus.ENABLE,
        component: '',
        permission: '',
        visible: true,
        keepAlive: true
    }
    formRef.value?.resetFields()
}


const open = async (type: string, id: number, parentId: number) => {
    dialogVisible.value = true
    type == "create" ? dialogTitle.value = "新增" : dialogTitle.value = "修改";
    formType.value = type
    resetForm()
    if (parentId) {
        formData.value.parentId = parentId
    }

    if (id) {
        const res = (await getMenuById(id)).data
        formData.value = res.data
    }

    await getMenuTree()
}
const defaultProps = {
    value: 'id',
    label: 'name',
    children: 'children',
    isLeaf: 'isLeaf',
}

const formRules = reactive({
    name: [{required: true, message: '菜单名称不能为空', trigger: 'blur'}],
    sort: [{required: true, message: '菜单顺序不能为空', trigger: 'blur'}],
    path: [{required: true, message: '路由地址不能为空', trigger: 'blur'}],
    status: [{required: true, message: '状态不能为空', trigger: 'blur'}]
})


const submitForm = async () => {
    if (!formRef) return
    formRef.value.validate(async (valid) => {
        if (!valid) return
        formLoading.value = true
        try {
            if (formType.value == "create") {
                await createMenu(formData.value)
                ElNotification.success("新增成功")
            } else {
                await updateMenu(formData.value)
                ElNotification.success("修改成功")
            }
            dialogVisible.value = false
            removeCache(CACHE_KEY.MENU_TREE)
            resetAuthStore()
            resetUserStore()
            emit("success")
        } finally {
            formLoading.value = false
        }
    })
}

const iconRef = ref()
const openIconSelector = () => {
    iconRef.value.open()
}

const iconChange = (e) => {
    formData.value.icon = e
}

defineExpose({
    open
})
</script>

<template>
    <el-dialog
            v-model="dialogVisible"
            :title="dialogTitle"
            :before-close="handleClose"
            draggable
            :close-on-click-modal="false"
            width="500px"
            align-center
    >
        <el-form
                ref="formRef"
                :model="formData"
                label-width="120px"
                class="demo-dynamic"
                :rules="formRules"
                v-loading="formLoading"
        >
            <el-form-item label="上级菜单">
                <el-tree-select
                        v-model="formData.parentId"
                        :data="menuTreeList"
                        :default-expanded-keys="[0]"
                        :props="defaultProps"
                        check-strictly
                        node-key="id"
                />

            </el-form-item>
            <el-form-item label="菜单名称" prop="name">
                <el-input v-model="formData.name" clearable placeholder="请输入菜单名称"/>
            </el-form-item>
            <el-form-item label="菜单类型" prop="type">
                <el-radio-group v-model="formData.type">
                    <el-radio-button
                            v-for="dict in getDictDataByDictType(DICT_TYPE.SYSTEM_MENU_TYPE)"
                            :key="dict.label"
                            :label="dict.value"
                    >
                        {{ dict.label }}
                    </el-radio-button>
                </el-radio-group>
            </el-form-item>
            <el-form-item v-if="formData.type != 3" prop="icon" label="菜单图标">
                <IconSelector ref="iconRef" @iconSelector="iconChange"></IconSelector>
                <span>

                <el-input v-model="formData.icon" @click="openIconSelector" placeholder="请选择图标" readonly>
                        <component :is="formData.icon" style="width: 20px; height: 20px"></component>
                </el-input>
                </span>
            </el-form-item>
            <el-form-item v-if="formData.type != 3" label="路由地址" prop="path">
                <el-input v-model="formData.path" clearable placeholder="请输入路由地址"/>
            </el-form-item>
            <el-form-item v-if="formData.type == 2" label="组件地址" prop="component">
                <el-input v-model="formData.component" clearable placeholder="例如说：system/user/index"/>
            </el-form-item>
            <el-form-item v-if="formData.type != 1" label="权限标识" prop="permission">
                <el-input v-model="formData.permission" clearable placeholder="请输入权限标识"/>
            </el-form-item>
            <el-form-item label="显示排序" prop="sort">
                <el-input-number v-model="formData.sort" :min="0" clearable controls-position="right"/>
            </el-form-item>

            <el-form-item label="菜单状态" prop="status">
                <el-radio-group v-model="formData.status">
                    <el-radio
                            v-for="dict in getDictDataByDictType(DICT_TYPE.COMMON_STATUS)"
                            :label="parseInt(<string>dict.value)"
                            :key="dict.value"
                    >
                        {{ dict.label }}
                    </el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item v-if="formData.type != 3" label="显示状态" prop="visible">
                <el-radio-group v-model="formData.visible">
                    <el-radio key="true" :label="true" border>显示</el-radio>
                    <el-radio key="false" :label="false" border>隐藏</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item v-if="formData.type == 2" label="缓存状态" prop="keepAlive">
                <el-radio-group v-model="formData.keepAlive">
                    <el-radio key="true" :label="true" border>缓存</el-radio>
                    <el-radio key="false" :label="false" border>不缓存</el-radio>
                </el-radio-group>
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button :disabled="formLoading" type="primary" @click="submitForm()">确 定</el-button>
            <el-button @click="dialogVisible = false">取 消</el-button>
        </template>
    </el-dialog>

</template>


<style scoped>
.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>