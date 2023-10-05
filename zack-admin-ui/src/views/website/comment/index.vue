<script setup lang="ts">
import {ref, Ref} from "vue";
import {ElMessageBox, FormInstance} from "element-plus";
import {deleteCommentById, list} from "@/api/website/comment";
import {CommentTreeListReqVO, CommentTreeListRespVO} from "@/api/website/comment/types.ts";

const commentTreeTable: Ref<Array<CommentTreeListRespVO>> = ref([])
const queryParams: Ref<CommentTreeListReqVO> = ref({
    articleId: undefined
})

const options = ref([])
const menuFormRef = ref<FormInstance>()

const loading = ref(true)

const commentList = async () => {
    const res = (await list(queryParams.value)).data
    commentTreeTable.value = res.data
    loading.value = false
}

commentList()

const handleQuery = () => {
    commentList()
}

const handleDelete = (row) => {
    ElMessageBox.confirm('确定要删除吗？',
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        })
        .then(async () => {
            const result = (await deleteCommentById(row.id)).data
            ElNotification.success(result.message)
            await commentList()
        }).catch(() => {

    })
}

const resetForm = (formEl: FormInstance | undefined) => {
    console.log("reset form")
    if (!formEl) return
    formEl.resetFields()
    handleQuery()
}

const formRef = ref()
const openForm = (type: string, id?: number, parentId?: number) => {
    formRef.value.open(type, id, parentId)
}
</script>

<template>
    <div class="comment-container">
        <el-form ref="menuFormRef" :inline="true" :model="queryParams" class="demo-form-inline">
            <el-form-item label="文章id" prop="articleId">

            </el-form-item>
            <el-form-item>
                <el-button v-hasPermission="['website:comment:query']" style="margin-left: 12px" class="button-global"
                           type="primary" icon="search" @click="handleQuery">
                    查询
                </el-button>
                <el-button @click="resetForm(menuFormRef)" icon="refresh">
                    重置
                </el-button>
            </el-form-item>

        </el-form>
        <el-row>
            <el-form-item>
                <el-button @click="openForm('create')" class="button-global" size="small" type="primary" icon="plus"
                           plain>新增
                </el-button>
            </el-form-item>
        </el-row>
        <el-row>
            <el-col>
                <el-table
                        table-layout="auto"
                        size="small"
                        :data="commentTreeTable"

                        row-key="id"
                        :header-cell-style="{backgroundColor: 'rgb(248, 248, 249)'}"
                        :cell-style="{fontSize: '10px'}"
                        lazy
                        v-loading="loading"
                        empty-text="暂无数据"
                        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
                >
                    <el-table-column prop="id" label="编号"/>
                    <el-table-column prop="articleName" label="文章名称"/>
                    <el-table-column prop="username" label="用户名"/>
                    <el-table-column prop="replyUsername" label="回复人"/>
                    <el-table-column label="操作" width="240">
                        <template #default="scope">
                            <el-button @click="openForm('update', scope.row.id, scope.row.parentId)" size="small"
                                       type="default" icon="edit" v-hasPermission="['website:comment:details']">
                                详情
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
    </div>
</template>

<style scoped>

</style>