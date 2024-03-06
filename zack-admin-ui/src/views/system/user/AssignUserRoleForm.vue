<script lang="ts" setup>
import {ref} from "vue";
import {UserPageRespVO} from "@/api/system/user/types.ts";
import {assignUserRole, getRoleList, getUserRoles} from "@/api/system/permission";

const dialogVisible = ref(false)
const formData = ref({
    userId: undefined | Number,
    username: "",
    nickname: "",
    roleIds: []
})
const formLoading = ref(false)

const roleOptions = ref([])

const selectData = ref([])

const submitForm = async () => {
    formLoading.value = true
    await assignUserRole(formData.value)
    ElNotification.success("分配角色成功")
    dialogVisible.value = false
    formLoading.value = false
}
const open = async (row: UserPageRespVO) => {
    dialogVisible.value = true
    roleOptions.value = []
    formData.value.userId = row.id
    formData.value.username = row.username
    formData.value.nickname = row.nickname

    roleOptions.value = (await getRoleList()).data.data
    formData.value.roleIds = (await getUserRoles(row.id)).data.data
}

defineExpose({open})
</script>

<template>
    <div class="assign-role-container">
        <el-dialog
                v-model="dialogVisible"
                title="分配角色"
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
                <el-form-item label="用户名称" prop="username">
                    <el-input v-model="formData.username" disabled/>
                </el-form-item>
                <el-form-item label="用户昵称" prop="nickname">
                    <el-input v-model="formData.nickname" disabled/>
                </el-form-item>
                <el-form-item label="角色" prop="roleIds">
                    <el-select
                            v-model="formData.roleIds"
                            multiple
                            placeholder="请选择角色"
                            style="width: 240px"
                    >
                        <el-option
                                v-for="item in roleOptions"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id"
                        />
                    </el-select>
                </el-form-item>
            </el-form>

            <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button :disabled="formLoading" type="primary" @click="submitForm()">
              确认
            </el-button>
          </span>
            </template>
        </el-dialog>
    </div>
</template>


<style scoped>

</style>