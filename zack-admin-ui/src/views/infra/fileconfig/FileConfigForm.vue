<script lang="ts" setup>
import {reactive, Ref, ref} from 'vue'
import type {FormRules} from 'element-plus'
import {createFileConfig, getFileConfigById, updateFileConfig} from "@/api/infra/fileconfig";
import {FileConfigRespVO} from "@/api/infra/fileconfig/types.ts";


const dialogTitle = ref("")

const formSize = ref('default')
const ruleFormRef = ref()
const formData: Ref<FileConfigRespVO> = ref({})

const formType = ref("")
const formLoading = ref(false)

const rules = reactive<FormRules>({
    name: [
        {required: true, message: '请输入配置名称', trigger: 'blur'},
    ],
    endpoint: [
        {
            type: "string",
            required: true,
            message: '请输入节点地址',
            trigger: 'blur',
        },
    ],
    domain: {
        type: "string",
        required: true,
        message: '请输入自定义域名',
        trigger: 'blur',
    },
    bucket: {
        type: "string",
        required: true,
        message: '请输入存储桶',
        trigger: 'blur',
    },
    accessKey: {
        type: "string",
        required: true,
        message: '请输入accessKey',
        trigger: 'blur',
    },
    accessSecret: {
        type: "string",
        required: true,
        message: '请输入accessSecret',
        trigger: 'blur',
    },
})


const resetForm = () => {
    formData.value = {
        id: undefined,
        name: "",
        endpoint: "",
        domain: "",
        bucket: "",
        accessKey: "",
        accessSecret: "",
        remark: undefined
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
        const resp = (await getFileConfigById(id)).data
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
                await createFileConfig(formData.value)
                ElNotification.success("新增成功")
            } else {
                await updateFileConfig(formData.value)
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
    <div class="notice-form-container">
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
                <el-form-item label="配置名称" prop="name">
                    <el-input v-model="formData.name" placeholder="请输入配置名称"/>
                </el-form-item>
                <el-form-item label="节点地址" prop="endpoint">
                    <el-input v-model="formData.endpoint" placeholder="请输入节点地址"/>
                </el-form-item>
                <el-form-item label="自定义域名" prop="domain">
                    <el-input v-model="formData.domain" placeholder="请输入自定义域名"/>
                </el-form-item>

                <el-form-item label="accessKey" prop="accessKey">
                    <el-input v-model="formData.accessKey" placeholder="请输入accessKey"/>
                </el-form-item>

                <el-form-item label="accessSecret" prop="accessSecret">
                    <el-input v-model="formData.accessSecret" placeholder="请输入accessSecret"/>
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
