<script setup lang="ts">
import DictTag from "@/components/DictTag/DictTag.vue";
import {DICT_TYPE} from "@/utils/dict.ts";
import {IssuesRespVO} from "@/api/website/issues/types.ts";
import {getIssuesById} from "@/api/website/issues";

const dialogVisible = ref(false)

const issuesDetailsData = ref<IssuesRespVO>()

const open = async (id: number) => {
    dialogVisible.value = true
    issuesDetailsData.value = (await getIssuesById(id)).data.data
}

defineExpose({open})
</script>

<template>
    <div class="issues-details-container">
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
                        label="issues编号"
                        label-align="center"
                        align="center"
                        label-class-name="my-label"
                        class-name="my-content"
                        width="100px"
                >
                    {{ issuesDetailsData?.id }}
                </el-descriptions-item>

                <el-descriptions-item label="用户名" label-align="center" align="center">
                    {{ issuesDetailsData?.username }}
                </el-descriptions-item>

                <el-descriptions-item label="标题" label-align="center" align="center">
                    {{issuesDetailsData?.title}}
                </el-descriptions-item>

                <el-descriptions-item label="内容" label-align="center" align="center">
                    {{issuesDetailsData?.content}}
                </el-descriptions-item>

                <el-descriptions-item label="状态" label-align="center" align="center">
                    <dict-tag :type="DICT_TYPE.WEBSITE_ISSUES_STATUS" :value="String(issuesDetailsData?.status)"/>
                </el-descriptions-item>

                <el-descriptions-item label="类型" label-align="center" align="center">
                    <dict-tag :type="DICT_TYPE.WEBSITE_ISSUES_TYPE" :value="String(issuesDetailsData?.type)"/>
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