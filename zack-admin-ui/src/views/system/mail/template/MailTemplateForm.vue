<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormRules} from 'element-plus'
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {createMailTemplate, getMailTemplateById, updateMailTemplate} from "@/api/system/mail/template";
import Editor from "@/components/Editor/Editor.vue";

const dialogTitle = ref("")

const formSize = ref('default')
const ruleFormRef = ref()
const formData = ref({
    id: undefined,
    code: "",
    name: "",
    nickname: "",
    accountId: undefined,
    title: "",
    content: "",
    status: 0,
    remark: ""
})

const formType = ref("")
const formLoading = ref(false)

const rules = reactive<FormRules>({
    code: [
        {required: true, message: '请输入模板编码', trigger: 'blur'},
    ],
    name: [
        {
            type: "string",
            required: true,
            message: '请输入模板名称',
            trigger: 'blur',
        },
    ],
    accountId: [
        {
            type: "number",
            required: true,
            message: '请选择邮箱账号',
            trigger: 'blur',
        },
    ],
    title: [
        {
            type: "string",
            required: true,
            message: '请输入标题',
            trigger: 'blur',
        },
    ],
    content: [
        {
            type: "string",
            required: true,
            message: '请输入内容',
            trigger: 'blur',
        },
    ],

    status: [
        {
            type: "number",
            required: true,
            message: '请选择状态',
            trigger: 'blur',
        },
    ],
})

const resetForm = () => {
    formData.value = {
        id: undefined,
        code: "",
        name: "",
        nickname: "",
        accountId: undefined,
        title: "",
        content: "",
        status: 0,
        remark: ""
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
        const resp = (await getMailTemplateById(id)).data
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
                await createMailTemplate(formData.value)
                ElNotification.success("新增成功")
            } else {
                await updateMailTemplate(formData.value)
                ElNotification.success("修改成功")
            }
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
    <div class="mail-template-form-container">
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
                <el-form-item label="模板编码" prop="code">
                    <el-input v-model="formData.code" placeholder="请输入模板编码"/>
                </el-form-item>
                <el-form-item label="模板名称" prop="name">
                    <el-input v-model="formData.name" placeholder="请输入模板名称"/>
                </el-form-item>
                <el-form-item label="发送人" prop="nickname">
                    <el-input v-model="formData.nickname" placeholder="请输入发送人"/>
                </el-form-item>
                <el-form-item label="邮箱账号" prop="accountId">
                    <el-select
                        placeholder="请选择邮箱"
                        v-model="formData.accountId"
                        clearable
                    >
                        <el-option
                            v-for="item in mailAccountList"
                            :key="item.id"
                            :label="item.email"
                            :value="item.id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="标题" prop="title">
                    <el-input v-model="formData.title" placeholder="请输入标题"/>
                </el-form-item>
                <el-form-item label="内容" prop="content">
                    <Editor style="border: 1px solid #cccccc; overflow: auto; "  :modelValue="formData.content" @update:modelValue="updateModelValue"/>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-radio-group v-model="formData.status">
                        <el-radio
                                v-for="dict in getDictDataByDictType(DICT_TYPE.COMMON_STATUS)"
                                :key="dict.label"
                                :label="parseInt(dict.value)"
                        >
                            {{ dict.label }}
                        </el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="备注" prop="remark">
                    <el-input type="textarea" v-model="formData.remark" placeholder="请输入备注"/>
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
