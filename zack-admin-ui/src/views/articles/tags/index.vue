<script setup lang="ts">

import {checkPermi} from "@/utils/permission.ts";
import {Ref} from "vue";
import {ElMessageBox, FormInstance} from "element-plus";
import {TagsPageReqVO, TagsPageRespVO} from "@/api/articles/tags/types.ts";
import {deleteTagsById, page} from "@/api/articles/tags";
import TagsForm from "@/views/articles/tags/TagsForm.vue";

const total = ref<number>(0);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const queryParams: Ref<TagsPageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
})

const tagsQueryFormRef = ref<FormInstance>()
const pageListData = ref<TagsPageRespVO[]>([])
const tagsFormRef = ref()
const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
    handleQuery()
}

const pageList = async () => {
    const resp = (await page(queryParams.value)).data
    pageListData.value = resp.data.data
    total.value = resp.data.total
}
const handleQuery = () => {
    pageList()
}

const sizeChange = (size: number) => {
    pageSize.value = size;
}

const currentPageChange = (page: number) => {
    currentPage.value = page;
}

watch([currentPage, pageSize], ([newCurrentPage, newPageSize], [oldCurrentPage, oldPageSize]) => {
    queryParams.value.pageNum = newCurrentPage
    queryParams.value.pageSize = newPageSize
    pageList()
})

const openForm = (type: string, id?: number) => {
    tagsFormRef.value.open(type, id)
}

const deleteTags = (row) => {
    ElMessageBox.confirm(`确定要删除分类名称为[${row.tagName}]吗？`,
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
        })
        .then(async () => {
            await deleteTagsById(row.id)
            ElNotification.success("删除成功")
            await pageList()
        }).catch(() => {

    })
}
pageList()
</script>

<template>
  <div class="tags-container">
      <el-form ref="tagsQueryFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
          <el-form-item label="标签名称" prop="tagName">
              <el-input v-model="queryParams.tagName" placeholder="请输入标签名称" style="width: 240px"/>
          </el-form-item>
          <el-form-item>
              <el-button v-hasPermission="['articles:tags:query']" class="button-global"
                         type="primary" icon="search" @click="handleQuery()">
                  查询
              </el-button>
              <el-button @click="resetForm(tagsQueryFormRef)" icon="refresh">
                  重置
              </el-button>
          </el-form-item>
      </el-form>

      <el-row>
          <el-button class="button-global" v-hasPermission="['articles:tags:create']"
                     size="small" type="primary" icon="plus" @click="openForm('create')"
                     plain>新增
          </el-button>
      </el-row>

      <el-table table-layout="fixed" :data="pageListData" size="small" style="width: 100%">
          <el-table-column prop="id" label="编号"/>
          <el-table-column prop="tagName" label="标签名称"/>
          <el-table-column prop="sort" label="排序"/>
          <el-table-column prop="createTime" label="创建时间"/>
          <el-table-column label="操作" width="240" align="center">

              <template #default="scope">
                  <el-button size="small" @click="openForm('update', scope.row.id)"
                             v-hasPermission="['articles:tags:update']">修改
                  </el-button>
                  <el-button @click="deleteTags(scope.row)" size="small" type="danger" icon="Delete"
                             v-if="checkPermi(['articles:tags:delete'])">
                      删除
                  </el-button>
              </template>
          </el-table-column>
      </el-table>

      <el-pagination background prev-text="上一页" next-text="下一页"
                     :page-sizes="pageSizes" :default-page-size="1"
                     :default-current-page="10"
                     :page-size="pageSize"
                     :current-page="currentPage"
                     layout="total, sizes, prev, pager, next, jumper" :total="total" @sizeChange="sizeChange"
                     @currentChange="currentPageChange"
                     style="display: flex; justify-content: flex-end; margin-top: 15px"
      />

      <TagsForm ref="tagsFormRef" @success="pageList" />
  </div>
</template>

<style scoped>

</style>