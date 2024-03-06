<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormRules} from 'element-plus'
import {createNotice, getNoticeById, updateNotice} from "@/api/website/notice";
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";

const dialogTitle = ref("")

const formSize = ref('default')
const ruleFormRef = ref()
const formData = ref({
    id: undefined,
    title: undefined,
    content: undefined,
    type: undefined
})

const formType = ref("")
const formLoading = ref(false)

const rules = reactive<FormRules>({
    title: [
        {required: true, message: '请输入标题', trigger: 'blur'},
    ],
    content: [
        {
            type: "string",
            required: true,
            message: '请输入内容',
            trigger: 'blur',
        },
        {
            max: 5000,
            required: true,
            message: "最大长度为5000个字符",
            trigger: 'blur',
        }
    ],
    type: {
        type: "number",
        required: true,
        message: '请选择类型',
        trigger: 'blur',
    },
})


const resetForm = () => {
    formData.value = {
        id: undefined,
        title: undefined,
        content: undefined,
        type: undefined
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
        const resp = (await getNoticeById(id)).data
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
                await createNotice(formData.value)
                ElNotification.success("新增成功")
            } else {
                await updateNotice(formData.value)
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
                <el-form-item label="类型" prop="type">

                    <el-select
                            :disabled="formType==='update'"
                            placeholder="请选择类型"
                            v-model="formData.type"
                            clearable
                    >
                        <el-option
                                v-for="item in getDictDataByDictType(DICT_TYPE.WEBSITE_NOTICE_TYPE)"
                                :key="item.value"
                                :label="item.label"
                                :value="parseInt(item.value)"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="标题" prop="title">
                    <el-input v-model="formData.title" placeholder="请输入标题"/>
                </el-form-item>
                <el-form-item label="内容" prop="content">
                    <el-input type="textarea" v-model="formData.content" placeholder="请输入内容"/>
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
