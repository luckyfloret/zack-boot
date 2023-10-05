<script setup lang="ts">
import DictTag from "@/components/DictTag/DictTag.vue";
import {DICT_TYPE} from "@/utils/dict.ts";
import {getOperateLogById} from "@/api/system/operatelog";
import {OperateLogRespVO} from "@/api/system/operatelog/types.ts";

const dialogVisible = ref(false)

const operateLogDetailsData = ref<OperateLogRespVO>()

const open = async (id: number) => {
    dialogVisible.value = true
    operateLogDetailsData.value = (await getOperateLogById(id)).data.data
}

defineExpose({open})
</script>

<template>
    <div class="operate-log-details-container">
        <el-dialog
                v-model="dialogVisible"
                title="详情"
                width="800"
                align-center
                draggable
                :close-on-click-modal="false"
                style="max-height: 600px;"

        >
            <el-descriptions :column="1" border style="max-height: 500px;overflow:auto;">
                <el-descriptions-item label="操作日志编号" label-align="center" align="left" min-width="150">
                    {{ operateLogDetailsData?.id }}
                </el-descriptions-item>

                <el-descriptions-item label="用户编号" label-align="center" align="left">
                    {{ operateLogDetailsData?.userId }}
                </el-descriptions-item>

                <el-descriptions-item label="用户类型" label-align="center" align="left">
                    <dict-tag :type="DICT_TYPE.USER_TYPE" :value="String(operateLogDetailsData?.userType)"/>
                </el-descriptions-item>

                <el-descriptions-item label="登录IP" label-align="center" align="left">
                    {{ operateLogDetailsData?.userIp }}
                </el-descriptions-item>

                <el-descriptions-item label="模块名" label-align="center" align="left">
                    {{ operateLogDetailsData?.module }}
                </el-descriptions-item>

                <el-descriptions-item label="操作名" label-align="center" align="left">
                    {{ operateLogDetailsData?.name }}
                </el-descriptions-item>

                <el-descriptions-item label="操作类型" label-align="center" align="left">
                    <dict-tag :type="DICT_TYPE.SYSTEM_OPERATE_TYPE" :value="String(operateLogDetailsData?.type)"/>
                </el-descriptions-item>

                <el-descriptions-item label="请求方式" label-align="center" align="left">
                    {{ operateLogDetailsData?.requestMethod }}
                </el-descriptions-item>

                <el-descriptions-item label="请求地址" label-align="center" align="left">
                    {{ operateLogDetailsData?.requestUrl }}
                </el-descriptions-item>

                <el-descriptions-item label="浏览器UA" label-align="center" align="left">
                    {{ operateLogDetailsData?.userAgent }}
                </el-descriptions-item>

                <el-descriptions-item label="java方法名" label-align="center" align="left">
                    {{ operateLogDetailsData?.javaMethod }}
                </el-descriptions-item>

                <el-descriptions-item label="java方法参数" label-align="center" align="left">
                    {{ operateLogDetailsData?.javaMethodArgs }}
                </el-descriptions-item>

                <el-descriptions-item label="操作结果" label-align="center" align="left">
                    {{ operateLogDetailsData?.result == 200 ? "成功" : `失败 (${operateLogDetailsData?.result})` }}
                </el-descriptions-item>

                <el-descriptions-item label="操作结果消息" label-align="center" align="left">
                    {{ operateLogDetailsData?.resultMsg }}
                </el-descriptions-item>

                <el-descriptions-item label="操作结果" label-align="center" align="left">
                    {{ operateLogDetailsData?.resultData }}
                </el-descriptions-item>

                <el-descriptions-item label="操作时间" label-align="center" align="left">
                    {{ operateLogDetailsData?.operateTime }}
                </el-descriptions-item>
            </el-descriptions>
        </el-dialog>
    </div>
</template>

<style scoped>
</style>