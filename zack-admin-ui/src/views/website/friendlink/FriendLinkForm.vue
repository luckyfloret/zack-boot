<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormRules} from 'element-plus'
import {CommonStatus} from "@/constants/CommonConstant.ts";
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {createFriendLink, getFriendLinkById, updateFriendLink} from "@/api/website/friendlink";

const urlPattern = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?";

const dialogTitle = ref("")

const formSize = ref('default')
const ruleFormRef = ref()
const formData = ref({
    id: undefined,
    name: "",
    description: "",
    avatarUrl: "",
    email: "",
    websiteUrl: "",
    sort: 0
})

const formType = ref("")
const formLoading = ref(false)

const rules = reactive<FormRules>({
    name: [
        {required: true, message: '请输入网站名称', trigger: 'blur'},
    ],
    avatarUrl: [
        {
            type: "string",
            required: true,
            message: '请输入头像地址',
            trigger: 'blur',
        },
        {
            pattern: urlPattern,
            required: true,
            message: "请输入正确的URL",
            trigger: 'blur',
        }
    ],
    email: {
        type: "email",
        required: true,
        message: '请输入正确的邮箱地址',
        trigger: 'blur',
    },
    websiteUrl: [
        {
            type: "string",
            required: true,
            message: '请输入网站地址',
            trigger: 'blur',
        },
        {
            pattern: urlPattern,
            required: true,
            message: "请输入正确的URL",
            trigger: 'blur',
        }
    ],
    sort: {
        type: "number",
        required: true,
        message: '请选择排序',
        trigger: 'blur',
    },
})


const resetForm = () => {
    formData.value = {
        id: undefined,
        name: "",
        description: "",
        avatarUrl: "",
        email: "",
        websiteUrl: "",
        sort: 0
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
        const resp = (await getFriendLinkById(id)).data
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
                await createFriendLink(formData.value)
                ElNotification.success("新增成功")
            } else {
                await updateFriendLink(formData.value)
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
    <div class="friend-link-form-container">
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
                <el-form-item label="网站名称" prop="name">
                    <el-input v-model="formData.name" placeholder="请输入网站名称"/>
                </el-form-item>
                <el-form-item label="网站描述" prop="description">
                    <el-input v-model="formData.description" placeholder="请输入网站描述"/>
                </el-form-item>

                <el-form-item label="网站头像地址" prop="avatarUrl">
                    <el-input v-model="formData.avatarUrl" placeholder="请输入网站头像地址"/>
                </el-form-item>

                <el-form-item label="网站地址" prop="websiteUrl">
                    <el-input v-model="formData.websiteUrl" placeholder="请输入网站地址" clearable/>
                </el-form-item>

                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="formData.email" placeholder="请输入邮箱" clearable/>
                </el-form-item>

                <el-form-item label="排序" prop="sort">
                    <el-input-number :min="0" v-model="formData.sort" placeholder="请输入字典排序"/>
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
