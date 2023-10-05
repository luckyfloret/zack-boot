<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormRules} from 'element-plus'
import {createMailAccount, getMailAccountById, updateMailAccount} from "@/api/system/mail/account";
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";

const dialogTitle = ref("")

const formSize = ref('default')
const ruleFormRef = ref()
const formData = ref({
    id: undefined,
    email: "",
    username: "",
    password: "",
    port: "",
    host: "",
    sslEnable: true
})

const formType = ref("")
const formLoading = ref(false)

const rules = reactive<FormRules>({
    email: [
        {required: true, message: '请输入邮箱', trigger: 'blur'},
        {type: "email", message: "请输入正确的邮箱", trigger: 'blur'}
    ],
    username: [
        {
            type: "string",
            required: true,
            message: '请输入用户名',
            trigger: 'blur',
        },
    ],
    password: [
        {
            type: "string",
            required: true,
            message: '请输入密码',
            trigger: 'blur',
        },
    ],
    port: [
        {
            type: "string",
            required: true,
            message: '请输入SMTP服务器端口',
            trigger: 'blur',
        },
    ],
    host: [
        {
            type: "string",
            required: true,
            message: '请输入SMTP服务器域名',
            trigger: 'blur',
        },
    ],

    sslEnable: [
        {
            type: "boolean",
            required: true,
            message: '请选择ssl是否开启',
            trigger: 'blur',
        },
    ],
})

const resetForm = () => {
    formData.value = {
        id: undefined,
        email: "",
        username: "",
        password: "",
        port: "",
        host: "",
        sslEnable: true
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
        const resp = (await getMailAccountById(id)).data
        formData.value = resp.data
    }

}

const emit = defineEmits(['success'])

const submitForm = () => {
    if (!ruleFormRef) return
    ruleFormRef.value.validate(async (valid) => {
        if (!valid) return
        formLoading.value = true
        try {
            if (formType.value == "create") {
                await createMailAccount(formData.value)
                ElNotification.success("新增成功")
            } else {
                await updateMailAccount(formData.value)
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
    <div class="mail-account-form-container">
        <el-dialog
                v-model="dialogVisible"
                :title="dialogTitle"
                draggable
                :close-on-click-modal="false"
                width="600px"
                align-center
        >
            <el-form
                    ref="ruleFormRef"
                    :model="formData"
                    :rules="rules"
                    label-width="140px"
                    class="demo-ruleForm"
                    :size="formSize"
                    status-icon
                    v-loading="formLoading"
            >
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="formData.email" placeholder="请输入邮箱"/>
                </el-form-item>
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="formData.username" placeholder="请输入用户名"/>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="formData.password" placeholder="请输入密码"/>
                </el-form-item>
                <el-form-item label="SMTP服务器端口" prop="port">
                    <el-input v-model="formData.port" placeholder="请输入SMTP服务器端口"/>
                </el-form-item>
                <el-form-item label="SMTP服务器域名" prop="host">
                    <el-input v-model="formData.host" placeholder="请输入SMTP服务器域名"/>
                </el-form-item>
                <el-form-item label="SSL" prop="sslEnable">
                    <el-radio-group v-model="formData.sslEnable">
                        <el-radio
                            v-for="dict in getDictDataByDictType(DICT_TYPE.COMMON_STATUS)"
                            :key="dict.label"
                            :label="dict.value == 0"
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
    </div>
</template>


<style scoped>
.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>
