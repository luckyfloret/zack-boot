<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormRules} from 'element-plus'
import {createTags, getTagsById, updateTags} from "@/api/articles/tags";

const dialogTitle = ref("")

const formSize = ref('default')
const ruleFormRef = ref()
const formData = ref({
    id: undefined,
    tagName: undefined,
    sort: 0,
})

const formType = ref("")
const formLoading = ref(false)

const rules = reactive<FormRules>({
    tagName: [
        {required: true, message: '请输入标签名称', trigger: 'blur'},
    ],
    sort: [
        {
            type: "number",
            required: true,
            message: '请输入排序号',
            trigger: 'blur',
        },
    ],
})


const resetForm = () => {
    formData.value = {
        id: undefined,
        tagName: undefined,
        sort: 0,
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
        const resp = (await getTagsById(id)).data
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
                await createTags(formData.value)
                ElNotification.success("新增成功")
            } else {
                await updateTags(formData.value)
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
    <div class="tags-form-container">
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
                <el-form-item label="标签名称" prop="tagName">
                    <el-input v-model="formData.tagName" placeholder="请输入标签名称"/>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                    <el-input-number v-model="formData.sort" :min="0" clearable controls-position="right"/>
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
