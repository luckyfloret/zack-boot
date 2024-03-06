<script lang="ts" setup>
import {ref} from 'vue'
import {assignMenuPermission, getMenuList, getRolePermission} from "@/api/system/permission";
import {PermissionMenuAssignReqVO} from "@/api/system/permission/types.ts";

interface Tree {
    id: number,
    name: string,
    parentId: number,
    children?: Tree[]
}

const dialogVisible = ref(false)
const formData = ref({
    id: undefined | Number,
    name: "",
    code: "",
    menuIds: []
})
const formLoading = ref(false)

const menuOptions = ref<Tree[]>([])

const treeRef = ref()
const emit = defineEmits(['success'])

const defaultProps = {
    children: 'children',
    label: 'name',
    value: 'id'
}

const menuExpand = ref(false) // 展开/折叠
const treeNodeAll = ref(false) // 全选/全不选
const menuList = async () => {
    const resp = (await getMenuList()).data
    const menuList: Tree[] = resp.data
    //先找出所有父节点
    menuList.forEach(item => {
        if (item.parentId === 0) {
            menuOptions.value.push(item)
        }
    })

    menuOptions.value.forEach(menu => {
        menu.children = getChildNode(menu.id, menuList)
    })
}


const getChildNode = (parentId: number, menuList: Tree[]): Tree[] => {
    const treeNode: Tree[] = []
    menuList.forEach(item => {
        if (parentId === item.parentId) {
            treeNode.push(item)
            item.children = getChildNode(item.id, menuList)
        }
    })

    return treeNode
}

const submitForm = async () => {
    let reqVO: PermissionMenuAssignReqVO = {
        roleId: formData.value.id,
        menuIds: [
            ...(treeRef.value.getCheckedKeys(false) as unknown as Array<number>), // 获得当前选中节点
            ...(treeRef.value.getHalfCheckedKeys() as unknown as Array<number>) // 获得半选中的父节点
        ]
    }
    await assignMenuPermission(reqVO)
    ElNotification.success("菜单权限分配成功")
    dialogVisible.value = false
    emit("success")

}
const open = async (role) => {
    dialogVisible.value = true
    menuOptions.value = []
    await menuList()
    formData.value.id = role.id
    formData.value.name = role.name
    formData.value.code = role.code

    const resp = (await getRolePermission(role.id)).data
    formData.value.menuIds = resp.data
}


/** 全选/全不选 */
const handleCheckedTreeNodeAll = () => {
    treeRef.value.setCheckedNodes(treeNodeAll.value ? menuOptions.value : [])
}

/** 展开/折叠全部 */
const handleCheckedTreeExpand = () => {
    const nodes = treeRef.value?.store.nodesMap
    for (let node in nodes) {
        if (nodes[node].expanded === menuExpand.value) {
            continue
        }
        nodes[node].expanded = menuExpand.value
    }
}

defineExpose({open})

</script>

<template>
    <el-dialog
            v-model="dialogVisible"
            title="菜单权限"
            width="500px"
            align-center
            draggable
            :close-on-click-modal="false"
    >
        <el-form
                :model="formData"
                label-width="120px"
                class="demo-ruleForm"
                status-icon
                v-loading="formLoading"
        >
            <el-form-item label="角色名称" prop="name">
                <el-input v-model="formData.name" disabled/>
            </el-form-item>
            <el-form-item label="角色编码" prop="code">
                <el-input v-model="formData.code" disabled/>
            </el-form-item>
            <el-form-item label="菜单权限" prop="permission">
                <div style="display: flex; width: 100%">
                    <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand">展开/折叠</el-checkbox>
                    <el-checkbox v-model="treeNodeAll" @change="handleCheckedTreeNodeAll">全选/全不选</el-checkbox>
                </div>
                <el-tree
                        style="border: 1px solid #e5e6e7; border-radius: 4px;margin-top: 5px; width: 100%"
                        ref="treeRef"
                        :data="menuOptions"
                        show-checkbox
                        node-key="id"
                        :default-checked-keys="formData.menuIds"
                        :props="defaultProps"
                        empty-text="加载中，请稍候"
                />
            </el-form-item>
        </el-form>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitForm()">
              确认
            </el-button>
          </span>
        </template>
    </el-dialog>
</template>


<style scoped>
.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>
