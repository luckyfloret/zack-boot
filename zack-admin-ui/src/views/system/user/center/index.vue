<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {TabsPaneContext} from 'element-plus'
import {FormRules} from "element-plus";
import {UserCenterVO} from "@/api/system/user/types.ts";
import {getUserInfo, updateUserBasicInfo, updateUserPassword} from "@/api/system/user/user-center.ts";
import DictTag from "@/components/DictTag/DictTag.vue";
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {removeCache} from "@/utils/cache.ts";
import {CACHE_KEY} from "@/hooks/useCache.ts";
import {resetUserStore} from "@/utils/store.ts";

const activeName = ref('first')
const formLoading = ref(false)
const formSize = ref('default')
const basicInfoPassFormRef = ref()
const updatePassFormRef = ref()
const updatePassFormData = ref({
    oldPassword: "",
    newPassword: "",
    confirmPassword: ""
})

const basicInfoFormData = ref({
    nickname: "",
    mobile: "",
    email: "",
    sex: undefined
})
const userInfo = ref<UserCenterVO>()
const init = async () => {
    const res = (await getUserInfo()).data
    userInfo.value = res.data
    basicInfoFormData.value.nickname = res.data.nickname
    basicInfoFormData.value.mobile = res.data.mobile
    basicInfoFormData.value.email = res.data.email
    basicInfoFormData.value.sex = res.data.sex
}

const submitForm = (type: string) => {
    formLoading.value = true
    if (type === "updateUserInfo") {
        if (!basicInfoPassFormRef) return
        basicInfoPassFormRef.value.validate(async (valid) => {
            try {
                if (valid) {
                    await updateUserBasicInfo(basicInfoFormData.value)
                    ElNotification.success("修改信息成功")
                    removeCache(CACHE_KEY.USER)
                    resetUserStore()
                }
            }finally {
                formLoading.value = false
            }
        })
    }else {
        if (!updatePassFormRef) return;
        updatePassFormRef.value.validate(async (valid) => {
            try {
                if (valid) {
                    await updateUserPassword(updatePassFormData.value)
                    ElNotification.success("修改密码成功")
                }
            }finally {
                formLoading.value = false
            }
        })
    }
}

const validatePassword = (rule: any, value: any, callback: any) => {
    if (value.indexOf(" ") !== -1) {
        callback(new Error("密码不能含有空格"))
    } else {
        callback()
    }
}
const validatePasswordIsConsistent = (rule: any, value: any, callback: any) => {
    if (value.trim() === "") {
        callback(new Error('请输入确认密码'))
    } else if (value !== updatePassFormData.value.newPassword) {
        callback(new Error("两次新密码不一致"))
    } else {
        callback()
    }
}

const updatePassRules = reactive<FormRules>({
    oldPassword: [
        {required: true, message: '请输入旧密码', trigger: 'blur'},
        {min: 6, max: 20, message: "长度是6-20位", trigger: 'blur'},
        {validator: validatePassword, trigger: 'blur'}
    ],
    newPassword: [
        {required: true, message: '请输入新密码', trigger: 'blur'},
        {min: 6, max: 20, message: "长度是6-20位", trigger: 'blur'},
        {validator: validatePassword, trigger: 'blur'}
    ],
    confirmPassword: [
        {required: true, validator: validatePasswordIsConsistent, trigger: 'blur'},
        {validator: validatePassword, trigger: 'blur'}
    ],
})

const basicInfoRules = reactive<FormRules>({
    nickname: [
        {required: true, message: '请输入昵称', trigger: 'blur'},
        {max: 20, message: "最大长度是20位", trigger: 'blur'},
    ],
    mobile: [
        {required: true, message: '请输入手机号', trigger: 'blur'},
        {min: 11, max: 11, message: "长度是11位", trigger: 'blur'},
    ],
    email: [
        {required: true, type: "email", message:"请输入邮箱", trigger: 'blur'},
    ],
})


onMounted(async () => {
    await init()
})
const handleClick = (tab: TabsPaneContext, event: Event) => {
    console.log(tab, event)
}
</script>

<template>
    <div class="user-center-container">
        <el-row>
            <el-col :xs="24" :sm="8" :md="8" :lg="8" :xl="8">
                <el-card class="box-card" shadow="hover">
                    <template #header>
                        <div class="card-header">
                            <span>
                                个人信息
                            </span>
                        </div>
                    </template>
                    <div class="personal-info-wrapper">
                        <ul class="text">

                            <li>
                                <el-icon>
                                    <component is="user"></component>
                                </el-icon>
                                用户名称
                            </li>
                            <li>{{ userInfo?.username }}</li>
                        </ul>
                        <ul class="text">
                            <li>
                                <el-icon>
                                    <component is="iphone"></component>
                                </el-icon>
                                手机号
                            </li>
                            <li>{{ userInfo?.mobile }}</li>
                        </ul>
                        <ul class="text">
                            <li>
                                <el-icon>
                                    <component is="message"></component>
                                </el-icon>
                                邮箱
                            </li>
                            <li>{{ userInfo?.email }}</li>
                        </ul>
                        <ul class="text">
                            <li>
                                <i class="iconfont icon-yonghuleixing" />
                                用户类型
                            </li>
                            <li>
                                <dict-tag :type="DICT_TYPE.USER_TYPE" :value="String(userInfo?.type)"></dict-tag>
                            </li>
                        </ul>
                        <ul class="text">
                            <li>
                                <i class="iconfont icon-role-group"/>
                                所属角色
                            </li>
                            <li>{{ userInfo?.roles.map(role => role.name).join(",") }}</li>
                        </ul>
                        <ul class="text">
                            <li>
                                <el-icon>
                                    <component is="location"></component>
                                </el-icon>
                                登录ip
                            </li>
                            <li>{{ userInfo?.loginIp }}</li>
                        </ul>
                        <ul class="text">
                            <li>
                                <el-icon>
                                    <component is="Calendar"></component>
                                </el-icon>
                                最后登录时间
                            </li>
                            <li>{{ userInfo?.loginDate }}</li>
                        </ul>
                        <ul class="text">
                            <li>
                                <el-icon>
                                    <component is="Calendar"></component>
                                </el-icon>
                                创建时间
                            </li>
                            <li>{{ userInfo?.createTime }}</li>
                        </ul>
                    </div>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="16" :md="16" :lg="16" :xl="16">
                <el-card class="box-card" shadow="hover">
                    <template #header>
                        <div class="card-header">
                            <span>
                                基本资料
                            </span>
                        </div>
                    </template>
                    <div class="basic-info-wrapper">
                        <el-tabs style="width: 100%" v-model="activeName" class="demo-tabs" @tab-click="handleClick">
                            <el-tab-pane label="基本资料" name="first">
                                <el-form
                                        ref="basicInfoPassFormRef"
                                        :model="basicInfoFormData"
                                        :rules="basicInfoRules"
                                        label-width="120px"
                                        :size="formSize"
                                        status-icon
                                        v-loading="formLoading"
                                >
                                    <el-form-item label="昵称" prop="nickname">
                                        <el-input v-model="basicInfoFormData.nickname" placeholder="请输入昵称"/>
                                    </el-form-item>
                                    <el-form-item label="手机号" prop="mobile">
                                        <el-input v-model="basicInfoFormData.mobile" placeholder="请输入手机号"/>
                                    </el-form-item>
                                    <el-form-item label="邮箱" prop="email">
                                        <el-input  v-model="basicInfoFormData.email" placeholder="请输入邮箱"/>
                                    </el-form-item>
                                    <el-form-item label="状态" prop="sex">
                                        <el-radio-group v-model="basicInfoFormData.sex">
                                            <el-radio
                                                v-for="dict in getDictDataByDictType(DICT_TYPE.SYSTEM_USER_SEX)"
                                                :key="dict.label"
                                                :label="parseInt(<string>dict.value)"
                                            >
                                                {{ dict.label }}
                                            </el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                </el-form>
                                <el-button type="primary" @click="submitForm('updateUserInfo')">保存</el-button>
                            </el-tab-pane>
                            <el-tab-pane label="修改密码" name="second">
                                <el-form
                                        ref="updatePassFormRef"
                                        :model="updatePassFormData"
                                        :rules="updatePassRules"
                                        label-width="120px"
                                        :size="formSize"
                                        status-icon
                                        v-loading="formLoading"
                                >
                                    <el-form-item label="旧密码" prop="oldPassword">
                                        <el-input show-password type="password" v-model="updatePassFormData.oldPassword"
                                                  placeholder="请输入旧密码"/>
                                    </el-form-item>
                                    <el-form-item label="新密码" prop="newPassword">
                                        <el-input show-password type="password" v-model="updatePassFormData.newPassword"
                                                  placeholder="请输入新密码"/>
                                    </el-form-item>
                                    <el-form-item label="确认密码" prop="confirmPassword">
                                        <el-input show-password v-model="updatePassFormData.confirmPassword"
                                                  type="password"
                                                  placeholder="请输入确认密码"/>
                                    </el-form-item>
                                </el-form>
                                <el-button :disabled="formLoading" type="primary" @click="submitForm('updatePassword')">保存</el-button>
                            </el-tab-pane>

                        </el-tabs>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<style scoped>
.card-header {
    /*display: flex;*/
    /*justify-content: space-between;*/
    /*align-items: center;*/
}

.demo-tabs > .el-tabs__content {
    padding: 32px;
    color: #6b778c;
    font-size: 32px;
    font-weight: 600;
}

.text {
    font-size: 14px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    list-style: none;
    border-bottom: 1px solid var(--el-border-color);
}

.item {
    margin-bottom: 18px;
}

.box-card {
    /*width: 600px;*/
}
</style>