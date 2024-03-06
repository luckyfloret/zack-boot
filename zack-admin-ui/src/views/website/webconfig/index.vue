<script setup lang="ts">
import {AuthorInfoVO, WebsiteInfoVO} from "@/api/website/webconfig/types.ts";
import {FormInstance, FormRules} from "element-plus";
import {Ref} from "vue";
import type {UploadProps} from 'element-plus'
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {getAccessToken} from "@/utils/auth.ts";
import {getAuthorInfo, getWebsiteInfo, updateAuthorInfo, updateWebsiteInfo} from "@/api/website/webconfig";

const websiteFormData: Ref<WebsiteInfoVO> = ref({})
const authorFormData: Ref<AuthorInfoVO> = ref({})
const websiteFormRef = ref()
const authorFormRef = ref()
const uploadHeaders = ref()

const url = import.meta.env.VITE_UPLOAD_URL
const websiteRules = reactive<FormRules<WebsiteInfoVO>>({
    title: [
        {required: true, message: '请输入网站标题', trigger: 'blur'},
        {max: 100, message: '最大长度100个字', trigger: 'blur'},
    ],

    name: [
        {required: true, message: '请输入网站名称', trigger: 'blur'},
        {max: 100, message: '最大长度100个字', trigger: 'blur'},
    ],

    isEnabledComment: [
        {required: true, message: '请选择评论功能', trigger: 'blur'},
    ],

    isEnabledTipping: [
        {required: true, message: '请选择打赏功能', trigger: 'blur'},
    ],
})

const authorRules = reactive<FormRules<WebsiteInfoVO>>({
    authorName: [
        {required: true, message: '请输入作者名称', trigger: 'blur'},
        {max: 100, message: '最大长度100个字', trigger: 'blur'},
    ],

    email: {type: "email", required: true, message: '请输入正确的邮箱', trigger: 'blur'},
})

const imageUrl = ref('')

const handleAlipayImage: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {
    websiteFormData.value.alipay = response.data
}

const handleWechatPayImage: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {
    websiteFormData.value.wechatPay = response.data
}

const handleAuthorAvatarImage: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {
    authorFormData.value.authorAvatar = response.data
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
    if (rawFile.size / 1024 / 1024 > 2) {
        ElMessage.error('Avatar picture size can not exceed 2MB!')
        return false
    }
    uploadHeaders.value = {
        Authorization: "Bearer " + getAccessToken()
    }
    return true
}

const getWebsite = async () => {
    const res = (await getWebsiteInfo()).data
    if (res.data) {
        websiteFormData.value = res.data;
    }
}

const getAuthor = async () => {
    const res = (await getAuthorInfo()).data
    if (res.data) {
        authorFormData.value = res.data;
    }
}
const init = () => {
    getWebsite()
    getAuthor()
}

const websiteSubmitForm = () => {
    if (!websiteFormRef) return

    websiteFormRef.value.validate(async (valid) => {
        if (valid) {
            await updateWebsiteInfo(websiteFormData.value)
            await getWebsite()
            ElNotification.success("保存成功")
        } else {
            console.log("websiteSubmitForm error...")
        }
    })
}

const authorSubmitForm = () => {
    if (!authorFormRef) return

    authorFormRef.value.validate(async (valid) => {
        if (valid) {
            await updateAuthorInfo(authorFormData.value)
            await getAuthor()
            ElNotification.success("保存成功")
        } else {
            console.log("websiteSubmitForm error...")
        }
    })
}

init()
</script>

<template>
    <div class="web-config-container">
        <el-tabs type="border-card">
            <el-tab-pane label="网站信息">
                <el-form
                        ref="websiteFormRef"
                        :model="websiteFormData"
                        label-width="120px"
                        class="demo-ruleForm"
                        status-icon
                        :rules="websiteRules"
                >
                    <el-form-item label="网站标题" prop="title">
                        <el-input v-model="websiteFormData.title" placeholder="请输入网站标题"/>
                    </el-form-item>

                    <el-form-item label="网站名称" prop="name">
                        <el-input v-model="websiteFormData.name" placeholder="请输入网站名称"/>
                    </el-form-item>

                    <el-form-item label="网站地址" prop="websiteUrl">
                        <el-input v-model="websiteFormData.websiteUrl" placeholder="请输入网站地址"/>
                    </el-form-item>

                    <el-form-item label="网站描述" prop="description">
                        <el-input v-model="websiteFormData.description" placeholder="请输入网站描述"/>
                    </el-form-item>

                    <el-form-item label="备案号" prop="recordNumber">
                        <el-input v-model="websiteFormData.recordNumber" placeholder="请输入备案号"/>
                    </el-form-item>
                    <el-form-item label="支付宝收款码" prop="alipay">
                        <el-upload
                                v-hasPermission="['website:web-config:upload']"
                                class="uploader"
                                :action="url"
                                :show-file-list="false"
                                :on-success="handleAlipayImage"
                                :before-upload="beforeAvatarUpload"
                                :headers="uploadHeaders"
                        >
                            <img v-if="websiteFormData.alipay" :src="websiteFormData.alipay"
                                 style="max-width: 178px; max-height: 178px" class="alipay-img"/>
                            <el-icon v-else class="uploader-icon">
                                <Plus/>
                            </el-icon>
                        </el-upload>
                    </el-form-item>

                    <el-form-item label="微信收款码" prop="wechatPay">
                        <el-upload
                                v-hasPermission="['website:web-config:upload']"
                                class="uploader"
                                :action="url"
                                :show-file-list="false"
                                :on-success="handleWechatPayImage"
                                :before-upload="beforeAvatarUpload"
                                :headers="uploadHeaders"

                        >
                            <img v-if="websiteFormData.wechatPay" :src="websiteFormData.wechatPay"
                                 style="max-width: 178px; max-height: 178px" class="wechatPay-img"/>
                            <el-icon v-else class="uploader-icon">
                                <Plus/>
                            </el-icon>
                        </el-upload>
                    </el-form-item>

                    <el-form-item label="评论" prop="isEnabledComment">
                        <el-radio-group v-model="websiteFormData.isEnabledComment">
                            <el-radio
                                    v-for="dict in getDictDataByDictType(DICT_TYPE.COMMON_STATUS)"
                                    :key="dict.label"
                                    :label="parseInt(<string>dict.value)"
                            >
                                {{ dict.label }}
                            </el-radio>
                        </el-radio-group>
                    </el-form-item>

                    <el-form-item label="打赏" prop="isEnabledTipping">
                        <el-radio-group v-model="websiteFormData.isEnabledTipping">
                            <el-radio
                                    v-for="dict in getDictDataByDictType(DICT_TYPE.COMMON_STATUS)"
                                    :key="dict.label"
                                    :label="parseInt(<string>dict.value)"
                            >
                                {{ dict.label }}
                            </el-radio>
                        </el-radio-group>
                    </el-form-item>

                    <el-button style="margin: 0 auto; display: block" @click="websiteSubmitForm()" type="primary">保存
                    </el-button>
                </el-form>

            </el-tab-pane>
            <el-tab-pane label="作者信息">
                <el-form
                        ref="authorFormRef"
                        :model="authorFormData"
                        :rules="authorRules"
                        label-width="120px"
                        class="demo-ruleForm"
                        status-icon
                >
                    <el-form-item label="作者头像" prop="avatar">
                        <el-upload
                                v-hasPermission="['website:web-config:upload']"
                                class="uploader"
                                :action="url"
                                :show-file-list="false"
                                :on-success="handleAuthorAvatarImage"
                                :before-upload="beforeAvatarUpload"
                                :headers="uploadHeaders"
                        >
                            <img v-if="authorFormData.authorAvatar" :src="authorFormData.authorAvatar"
                                 style="max-width: 178px; max-height: 178px"/>
                            <el-icon v-else class="uploader-icon">
                                <Plus/>
                            </el-icon>
                        </el-upload>
                    </el-form-item>

                    <el-form-item label="作者名称" prop="authorName">
                        <el-input v-model="authorFormData.authorName" placeholder="请输入作者名称"/>
                    </el-form-item>

                    <el-form-item label="作者简介" prop="name">
                        <el-input v-model="authorFormData.authorSummary" placeholder="请输入作者简介"/>
                    </el-form-item>

                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="authorFormData.email" placeholder="请输入邮箱"/>
                    </el-form-item>

                    <el-form-item label="gitee地址" prop="giteeUrl">
                        <el-input v-model="authorFormData.giteeUrl" placeholder="gitee地址"/>
                    </el-form-item>

                    <el-form-item label="github地址" prop="githubUrl">
                        <el-input v-model="authorFormData.githubUrl" placeholder="github地址"/>
                    </el-form-item>

                    <el-form-item label="关于作者" prop="aboutAuthor">
                        <el-input v-model="authorFormData.aboutAuthor" placeholder="请输入关于作者的一些信息"
                                  type="textarea"/>
                    </el-form-item>

                    <el-button style="margin: 0 auto; display: block" @click="authorSubmitForm()"
                               v-hasPermission="['website:web-config:update']" type="primary">保存
                    </el-button>
                </el-form>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>
<style scoped>
.avatar-uploader .avatar {
    width: 50px;
    height: 50px;
    display: block;
}
</style>
<style>
.uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
}
</style>