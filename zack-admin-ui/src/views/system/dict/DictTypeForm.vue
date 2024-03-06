<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type { FormRules} from 'element-plus'
import {CommonStatus} from "@/constants/CommonConstant.ts";
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {createDictType, getById, updateDictType} from "@/api/system/dict/dict-type.ts";


const dialogTitle = ref("")

const formSize = ref('default')
const ruleFormRef = ref()
const formData = ref({
    id: undefined | Number,
    name: "",
    type: undefined,
    status: CommonStatus.ENABLE,
    remark: ""
})

const formType = ref("")
const formLoading = ref(false)

const rules = reactive<FormRules>({
    name: [
        {required: true, message: '请输入字典名称', trigger: 'blur'},
    ],
    status: [
        {
            type: "number",
            required: true,
            message: '请选择用户状态',
            trigger: 'blur',
        },
    ],
    type: [
        {
            type: "string",
            required: true,
            message: '请输入字典类型',
            trigger: 'blur',
        },
    ],
})


const resetForm = () => {
    formData.value = {
        id: undefined | Number,
        name: "",
        type: undefined,
        status: CommonStatus.ENABLE,
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
        const resp = (await getById(id)).data
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
                await createDictType(formData.value)
                ElNotification.success("新增成功")
            } else {
                await updateDictType(formData.value)
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
            <el-form-item label="字典名称" prop="name">
                <el-input v-model="formData.name" placeholder="请输入字典名称"/>
            </el-form-item>
            <el-form-item label="字典类型" prop="type">
                <el-input v-model="formData.type" placeholder="请输入字典类型"/>
            </el-form-item>

            <el-form-item label="状态" prop="status">
                <el-radio-group v-model="formData.status">
                    <el-radio
                        v-for="dict in getDictDataByDictType(DICT_TYPE.COMMON_STATUS)"
                        :key="dict.label"
                        :label="parseInt(<string>dict.value)"
                    >
                        {{ dict.label }}
                    </el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item label="备注" prop="remark">
                <el-input v-model="formData.remark" placeholder="请输入备注" clearable/>
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
</template>


<style scoped>
.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>
