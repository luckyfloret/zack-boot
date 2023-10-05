<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type { FormRules} from 'element-plus'
import {CommonStatus} from "@/constants/CommonConstant.ts";
import {DICT_TYPE, getDictDataByDictType, removeDictDataCache} from "@/utils/dict.ts";
import {createDictData, getDictDataById, updateDictData} from "@/api/system/dict/dict-data.ts";
import {resetDictStore} from "@/utils/store.ts";

const colorTypeOptions = readonly([
    {
        value: 'default',
        label: '默认'
    },
    {
        value: 'primary',
        label: '主要'
    },
    {
        value: 'success',
        label: '成功'
    },
    {
        value: 'info',
        label: '信息'
    },
    {
        value: 'warning',
        label: '警告'
    },
    {
        value: 'danger',
        label: '危险'
    }
])

const dialogTitle = ref("")
const formSize = ref('default')
const ruleFormRef = ref()
const formData = ref({
    id: undefined | Number,
    sort: 0,
    label: "",
    value: "",
    dictType: "",
    status: CommonStatus.ENABLE,
    colorType: "",
    remark: ""
})

const formType = ref("")
const formLoading = ref(false)

const rules = reactive<FormRules>({
    label: [
        {required: true, message: '请输入字典标签', trigger: 'blur'},
    ],
    value: [
        {required: true, message: '请输入字典键值', trigger: 'blur'},
    ],
    dictType: [
        {required: true, message: '请输入字典类型', trigger: 'blur'},
    ],
    colorType: [
        {required: true, message: '请输入字典名称', trigger: 'blur'},
    ],

    sort: [
        {required: true, message: '请输入字典排序', trigger: 'blur'},
    ],

    status: [
        {
            type: "number",
            required: true,
            message: '请选择字典数据状态',
            trigger: 'blur',
        },
    ],
})


const resetForm = () => {
    formData.value = {
        id: undefined | Number,
        sort: 0,
        label: "",
        value: "",
        dictType: "",
        status: CommonStatus.ENABLE,
        colorType: "",
        remark: ""
    }
    ruleFormRef.value?.resetFields()
}


const dialogVisible = ref(false)

const open = async (type: string, dictType: string, id: number) => {
    dialogVisible.value = true
    type == "create" ? dialogTitle.value = "新增" : dialogTitle.value = "修改"
    resetForm()
    formType.value = type

    if (dictType){
        formData.value.dictType = dictType
    }

    if (id) {
        const resp = (await getDictDataById(id)).data
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
                await createDictData(formData.value)
                ElNotification.success("新增成功")
            } else {
                await updateDictData(formData.value)
                ElNotification.success("修改成功")
            }
            dialogVisible.value = false;
            await emit("success")
            resetDictStore()
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

            <el-form-item label="字典类型" prop="dictType">
                <el-input disabled v-model="formData.dictType" placeholder="请输入字典类型"/>
            </el-form-item>

            <el-form-item label="字典标签" prop="label">
                <el-input v-model="formData.label" placeholder="请输入字典标签"/>
            </el-form-item>

            <el-form-item label="字典键值" prop="value">
                <el-input v-model="formData.value" placeholder="请输入字典键值"/>
            </el-form-item>


            <el-form-item label="字典排序" prop="sort">
                <el-input-number :min="0" v-model="formData.sort" placeholder="请输入字典排序"/>
            </el-form-item>
            <el-form-item label="颜色类型" prop="colorType">
                <el-select
                    placeholder="请选择颜色类型"
                    v-model="formData.colorType"
                >
                    <el-option
                        v-for="item in colorTypeOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
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