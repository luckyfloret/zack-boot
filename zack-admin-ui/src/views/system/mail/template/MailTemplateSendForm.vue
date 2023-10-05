<script setup lang="ts">
import {ElNotification, FormRules} from "element-plus";
import {getMailTemplateById, testSendMail} from "@/api/system/mail/template";
import Editor from "@/components/Editor/Editor.vue";

const dialogTitle = ref("")

const formSize = ref('default')
const ruleFormRef = ref()
const formData = ref({
    toMail: "",
    templateCode: "",
    content: "",
    params: {},
    templateParams: new Map()
})

const formType = ref("")
const formLoading = ref(false)

const rules = reactive<FormRules>({
    toMail: [
        {required: true, message: '请输入收件邮箱', trigger: 'blur'},
    ],
    templateCode: [
        {
            type: "string",
            required: true,
            message: '请输入模板编码',
            trigger: 'blur',
        },
    ],
    templateParams: {},
})

const resetForm = () => {
    formData.value = {
        toMail: "",
        templateCode: "",
        content: "",
        params: {},
        templateParams: {}
    }
    ruleFormRef.value?.resetFields()
}


const dialogVisible = ref(false)

const open = async (id: number) => {
    dialogVisible.value = true
    resetForm()
    try {
        // 设置动态表单
        const data = (await getMailTemplateById(id)).data.data

        formData.value.content = data.content
        formData.value.params = data.params
        formData.value.templateCode = data.code
        formData.value.templateParams = data.params.reduce((obj, item) => {
            obj[item] = '' // 给每个动态属性赋值，避免无法读取
            return obj
        }, {})
        rules.templateParams = data.params.reduce((obj, item) => {
            obj[item] = { required: true, message: '参数 ' + item + ' 不能为空', trigger: 'blur' }
            return obj
        }, {})
    } finally {
        formLoading.value = false
    }
}

const emit = defineEmits(['success'])

const submitForm = () => {
    if (!ruleFormRef) return
    ruleFormRef.value.validate(async (valid) => {
        if (!valid) return
        formLoading.value = true
        try {
            await testSendMail(formData.value);
            ElNotification.success("发送成功")
            dialogVisible.value = false;
            emit("success")
        } finally {
            formLoading.value = false
        }
    })
}

const updateModelValue = (value: string) => {
    formData.value.content = value
}

const props = defineProps({
    mailAccountList: Array
})

defineExpose({
    open
})
</script>

<template>
    <div class="mail-template-send-container">
        <el-dialog
                v-model="dialogVisible"
                :title="dialogTitle"
                draggable
                :close-on-click-modal="false"
                width="800px"
                align-center
                style="overflow: auto; height: 700px"
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
                <el-form-item label="内容" prop="code">
                    <Editor style="border: 1px solid #cccccc; overflow: auto; " :modelValue="formData.content" :readonly="true"/>
                </el-form-item>
                <el-form-item label="收件邮箱" prop="toMail">
                    <el-input v-model="formData.toMail" placeholder="请输入收件邮箱"/>
                </el-form-item>
                <el-form-item
                    v-for="param in formData.params"
                    :key="param"
                    :label="'参数 {' + param + '}'"
                    :prop="'templateParams.' + param"
                >
                    <el-input
                        v-model="formData.templateParams[param]"
                        :placeholder="'请输入 ' + param + ' 参数'"
                    />
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