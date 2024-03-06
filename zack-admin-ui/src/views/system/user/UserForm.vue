<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormInstance, FormRules} from 'element-plus'
import {CommonStatus, UserSex} from "@/constants/CommonConstant.ts";
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {createUser, getUserById, updateUser} from "@/api/system/user";


const dialogTitle = ref("")
const formSize = ref('default')
const ruleFormRef = ref()
const formData = ref({
    id: undefined | Number,
    username: "",
    nickname: "",
    email: "",
    mobile: "",
    sex: undefined,
    type: undefined,
    status: CommonStatus.ENABLE
})

const formType = ref("")
const formLoading = ref(false)

const rules = reactive<FormRules>({
    username: [
        {required: true, message: '请输入用户名称', trigger: 'blur'},
    ],
    nickname: [
        {
            required: true,
            message: '请输入用户昵称',
            trigger: 'blur',
        },
    ],
    email: [
        {
            type: 'email',
            required: true,
            message: '请输入邮箱',
            trigger: 'blur',
        },
    ],
    status: [
        {
            type: "number",
            required: true,
            message: '请选择用户状态',
            trigger: 'blur',
        },
    ],
    mobile: [
        {
            type: "string",
            pattern: /^(?:(?:\+|00)86)?1(?:3[\d]|4[5-79]|5[0-35-9]|6[5-7]|7[0-8]|8[\d]|9[189])\d{8}$/,
            message: '请输入正确的手机号码',
            required: true,
            trigger: 'blur',
        },
    ],
    sex: [
        {
            required: true,
            message: '请选择用户性别',
            trigger: 'blur',
        },
    ],
    type: [
        {
            type: "number",
            required: true,
            message: '请选择用户类型',
            trigger: 'blur',
        },
    ],
})


const resetForm = () => {
    formData.value = {
        id: undefined | Number,
        username: "",
        nickname: "",
        email: "",
        mobile: "",
        sex: undefined,
        type: undefined,
        status: CommonStatus.ENABLE
    }
    ruleFormRef.value?.resetFields()
}


const dialogVisible = ref(false)

const open = async (type: string, id: number) => {
    dialogVisible.value = true
    type == "create" ? dialogTitle.value = "新增" : dialogTitle.value = "修改"
    resetForm()
    formType.value = type

    if (id) {
        const resp = (await getUserById(id)).data
        formData.value = resp.data
    }

}

const emit = defineEmits(['success'])

const submitForm = () => {
    if (!ruleFormRef) return
    ruleFormRef.value.validate(async (valid) => {
        console.log("submit => ", valid)
        if (!valid) return
        formLoading.value = true
        try {
            if (formType.value == "create") {
                await createUser(formData.value)
                ElNotification.success("新增成功")
            } else {
                await updateUser(formData.value)
                ElNotification.success("修改成功")
            }
            dialogVisible.value = false;
            emit("success")
        } finally {
            formLoading.value = false
        }
    })
}

defineExpose({
    open
})
</script>

<template>
    <el-dialog
            v-model="dialogVisible"
            :title="dialogTitle"
            draggable
            :close-on-click-modal="false"
            width="500px"
            align-center
    >
        <el-form
                ref="ruleFormRef"
                :model="formData"
                :rules="rules"
                label-width="120px"
                class="demo-ruleForm"
                :size="formSize"
                status-icon
                v-loading="formLoading"
        >
            <el-form-item label="用户名称" prop="username">
                <el-input v-model="formData.username" placeholder="请输入用户名称"/>
            </el-form-item>
            <el-form-item label="用户昵称" prop="nickname">
                <el-input v-model="formData.nickname" placeholder="请输入用户昵称"/>
            </el-form-item>

            <el-form-item label="邮箱" prop="email">
                <el-input v-model="formData.email" placeholder="请输入邮箱" clearable/>
            </el-form-item>

            <el-form-item label="手机号" prop="mobile">
                <el-input v-model="formData.mobile" placeholder="请输入手机号"/>
            </el-form-item>

            <el-form-item label="用户性别" prop="sex">
                <el-select
                        placeholder="请选择用户性别"
                        v-model="formData.sex"
                        clearable
                >
                    <el-option
                            v-for="item in getDictDataByDictType(DICT_TYPE.SYSTEM_USER_SEX)"
                            :key="item.value"
                            :label="item.label"
                            :value="parseInt(item.value)"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="用户类型" prop="type" v-if="formType == 'create'">
                <el-select
                        placeholder="请选择用户类型"
                        v-model="formData.type"
                        clearable
                >
                    <el-option
                            v-for="item in getDictDataByDictType(DICT_TYPE.USER_TYPE)"
                            :key="item.value"
                            :label="item.label"
                            :value="parseInt(item.value)"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="状态" prop="status">
                <el-radio-group v-model="formData.status">
                    <el-radio
                            v-for="dict in getDictDataByDictType(DICT_TYPE.COMMON_STATUS)"
                            :key="dict.label"
                            :label="parseInt(<string>dict.value)"
                    >
                        {{ dict.label }}
                    </el-radio>
                </el-radio-group>
            </el-form-item>
        </el-form>

        <template #footer>
          <span class="dialog-footer">
              <el-button :disabled="formLoading" type="primary" @click="submitForm()">
              确 定
            </el-button>
            <el-button @click="dialogVisible = false">取消</el-button>

          </span>
        </template>
    </el-dialog>
</template>


<style scoped>
.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>