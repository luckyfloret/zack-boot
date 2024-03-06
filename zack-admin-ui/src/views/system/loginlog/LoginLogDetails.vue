<script setup lang="ts">
import {LoginLogRespVO} from "@/api/system/loginlog/types.ts";
import {getLoginLogById} from "@/api/system/loginlog";
import DictTag from "@/components/DictTag/DictTag.vue";
import {DICT_TYPE} from "@/utils/dict.ts";

const dialogVisible = ref(false)

const loginLogDetailsData = ref<LoginLogRespVO>()

const open = async (id: number) => {
    dialogVisible.value = true
    loginLogDetailsData.value = (await getLoginLogById(id)).data.data
}

defineExpose({open})
</script>

<template>
    <div class="login-log-details-container">
        <el-dialog
                v-model="dialogVisible"
                title="详情"
                width="800"
                align-center
                draggable
                :close-on-click-modal="false"

        >
            <el-descriptions :column="1" border>
                <el-descriptions-item
                        label="登录日志编号"
                        label-align="center"
                        align="center"
                        label-class-name="my-label"
                        class-name="my-content"
                        width="100px"
                >
                    {{ loginLogDetailsData?.id }}
                </el-descriptions-item>

                <el-descriptions-item label="用户名" label-align="center" align="center">
                    {{ loginLogDetailsData?.username }}
                </el-descriptions-item>

                <el-descriptions-item label="用户类型" label-align="center" align="center">
                    <dict-tag :type="DICT_TYPE.USER_TYPE" :value="String(loginLogDetailsData?.userType)"/>
                </el-descriptions-item>

                <el-descriptions-item label="登录类型" label-align="center" align="center">
                    <dict-tag :type="DICT_TYPE.SYSTEM_LOGIN_TYPE" :value="String(loginLogDetailsData?.loginType)"/>
                </el-descriptions-item>

                <el-descriptions-item label="登录IP" label-align="center" align="center">
                    {{loginLogDetailsData?.userIp}}
                </el-descriptions-item>

                <el-descriptions-item label="浏览器UA" label-align="center" align="center">
                    {{loginLogDetailsData?.userAgent}}
                </el-descriptions-item>

                <el-descriptions-item label="登录结果" label-align="center" align="center">
                    {{loginLogDetailsData?.result == 200 ? "成功" : "失败"}}
                </el-descriptions-item>

                <el-descriptions-item label="登录时间" label-align="center" align="center">
                    {{loginLogDetailsData?.createTime}}
                </el-descriptions-item>
            </el-descriptions>
        </el-dialog>
    </div>
</template>

<style scoped>
.my-label {
    background: var(--el-color-success-light-9);
}

.my-content {
    background: var(--el-color-danger-light-9);
}
</style>