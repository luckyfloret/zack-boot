<template>
    <div class="container">
        <el-row :gutter="10" class="box1">
            <el-col :xs="0" :sm="12" :md="12" :lg="14" :xl="14"
                    class="login-left-container hidden-xs-only">
                <div>
                    <h2 style="color: #fff;text-align: center   ">zack-blog后台管理系统</h2>
                    <img style="width: 100%" src="@/assets/login-cover.png" alt="">
                </div>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="10" :xl="10" class="login-right-container">
                <div style="text-align: center;  margin-left: 20px ">
                    <h1>欢迎登录</h1>
                </div>
                <el-form
                        ref="loginFormRef"
                        :model="ruleForm"
                        status-icon
                        :rules="rules"
                        label-width="60px"
                        class="login-form"
                >

                    <el-form-item label="用户名" prop="username" style="width: 320px">
                        <el-input v-model="ruleForm.username" placeholder="请输入用户名" type="username" autocomplete="off" style="height: 35px">
                            <template #prefix>
                                <el-icon>
                                    <component is="user"></component>
                                </el-icon>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password" style="width: 320px">
                        <el-input v-model="ruleForm.password" type="password" placeholder="请输入密码" autocomplete="off" @keyup.native.enter="enterEvent" style="height: 35px">
                            <template #prefix>
                                <el-icon>
                                    <component is="lock"></component>
                                </el-icon>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="getCaptchaCode()"
                                   style="width: 250px; height: 40px; border-radius: 20px">
                            登录
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>

        <Verify
            mode="pop"
            ref="verify"

            captchaType="blockPuzzle"
            :imgSize="{ width: '400px', height: '200px' }"
            @success="handleLogin"
        />
    </div>
</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue'
import {login} from "@/api/login";
import type {FormInstance, FormRules} from 'element-plus'
import {UserLoginReqVO} from "@/api/login/types.ts";
import {ElLoading, ElMessage} from "element-plus";
import {setToken} from '@/utils/auth.ts'
import {useRouter} from "vue-router";

const loginFormRef = ref()

const router = useRouter()
const verify = ref()
const validateUsername = (rule: any, value: any, callback: any) => {
    if (value.trim() === '') {
        callback(new Error('请输入用户名'))
    } else {
        callback()
    }
}
const validatePassword = (rule: any, value: any, callback: any) => {
    if (value.trim() === '') {
        callback(new Error('请输入密码'))
    } else {
        callback()
    }
}

const ruleForm: UserLoginReqVO = reactive({
    username: 'admin',
    password: '123456',
    captchaVerification: ''
})

//是否开启验证码
const captchaEnable = ref(true)

const rules = reactive<FormRules<typeof ruleForm>>({
    username: [{validator: validateUsername, trigger: 'blur'}],
    password: [{validator: validatePassword, trigger: 'blur'}],
})

const getCaptchaCode = async () => {
    if (captchaEnable) {
        verify.value.show()
    }else {
        await handleLogin({})
    }
}

const handleLogin = (params) => {
    const loading = ElLoading.service({
        lock: true,
        text: '加载中...',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    if (!loginFormRef) return
    loginFormRef.value.validate(async (valid) => {
        try {
            if (valid) {
                ruleForm.captchaVerification = params.captchaVerification
                let {data} = await login(ruleForm);
                setToken(data.data.accessToken, data.data.refreshToken)
                await router.push("/")
            } else {
                loading.close()
                return false
            }
        }finally {
            loading.close()
        }


    })
}

const enterEvent = () => {
    // submitForm(ruleFormRef.value)
}

const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
}
</script>

<style scoped>
.box1 {
    height: 100%;
}

.login-left-container {
    width: 100%;
    height: 100%;
    background-color: rgb(226, 127, 111);
    display: flex;
    align-items: center;
    justify-content: center;
}

.login-right-container {
    width: 100%;
    height: 100%;
    text-align: center;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    display: flex;
}

.container {
    height: 100vh;
    width: 100vw;
}

.login-form {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
}

@media (max-width: 768px) {
    .login-left-container {
        display: none;
    }
}
</style>