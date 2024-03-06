<script lang="ts" setup>
import {ref} from 'vue'
import {UploadFilled} from '@element-plus/icons-vue'
import {getAccessToken} from "@/utils/auth.ts";

const dialogTitle = ref("")
const requestHeader = ref()
const formLoading = ref(false)
const uploadRef = ref()
const requestUrl = import.meta.env.VITE_UPLOAD_URL
const fileList = ref([])
const dialogVisible = ref(false)
const emit = defineEmits(['success'])

const open = async () => {
    dialogVisible.value = true
    resetData()
}

const resetData = () => {
    // 重置上传状态和文件
    formLoading.value = false
    uploadRef.value?.clearFiles()

}

const uploadError = () => {
    ElNotification.error("文件上传失败")
    formLoading.value = false
}

const uploadExceed = () => {
    ElNotification.error("只能上传一个文件")
}

const submitUpload = () => {
    if (fileList.value.length == 0) {
        ElNotification.error("请上传文件")
        return;
    }

    requestHeader.value = {
        Authorization: "Bearer " + getAccessToken()
    }
    uploadRef.value.submit()
}

const uploadSuccess = () => {
    dialogVisible.value = false
    formLoading.value = false
    ElNotification.success("文件上传成功")
    emit('success')
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
            <el-upload
                    ref="uploadRef"
                    class="upload-file"
                    drag
                    :headers="requestHeader"
                    v-model:file-list="fileList"
                    :auto-upload="false"
                    :action="requestUrl"
                    :on-error="uploadError"
                    :on-exceed="uploadExceed"
                    accept=".jpg, .png, .gif"
                    :on-success="uploadSuccess"
                    :limit="1"
            >
                <el-icon class="el-icon--upload">
                    <upload-filled/>
                </el-icon>
                <div class="el-upload__text">
                    将文件拖到此处或 <em>点击上传</em>
                </div>
                <template #tip>
                    <div class="el-upload__tip">
                        只能上传jpg、png、gif文件
                    </div>
                </template>
            </el-upload>
            <template #footer>
          <span class="dialog-footer">
              <el-button :disabled="formLoading" type="primary" @click="submitUpload()">
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
