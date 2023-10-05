<script setup lang="ts">

import {checkPermi} from "@/utils/permission.ts";
import {ElMessageBox, FormInstance} from "element-plus";
import {Ref} from "vue";
import {CategoryPageReqVO, CategoryPageRespVO} from "@/api/articles/category/types.ts";
import {deleteCategoryById, page} from "@/api/articles/category";
import CategoryForm from "@/views/articles/category/CategoryForm.vue";

const total = ref<number>(0);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const queryParams: Ref<CategoryPageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
})

const categoryQueryFormRef = ref<FormInstance>()
const pageListData = ref<CategoryPageRespVO[]>([])
const categoryFormRef = ref()
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
    categoryFormRef.value.open(type, id)
}

const deleteCategory = (row) => {
    ElMessageBox.confirm(`确定要删除分类名称为[${row.categoryName}]吗？`,
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
        })
        .then(async () => {
            await deleteCategoryById(row.id)
            ElNotification.success("删除成功")
            await pageList()
        }).catch(() => {

    })
}
pageList()
</script>

<template>
  <div class="category-container">
      <el-form ref="categoryQueryFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
          <el-form-item label="分类名称" prop="categoryName">
              <el-input v-model="queryParams.categoryName" placeholder="请输入分类名称" style="width: 240px"/>
          </el-form-item>
          <el-form-item>
              <el-button v-hasPermission="['articles:category:query']" class="button-global"
                         type="primary" icon="search" @click="handleQuery()">
                  查询
              </el-button>
              <el-button @click="resetForm(categoryQueryFormRef)" icon="refresh">
                  重置
              </el-button>
          </el-form-item>
      </el-form>

      <el-row>
          <el-button class="button-global" v-hasPermission="['articles:category:create']"
                     size="small" type="primary" icon="plus" @click="openForm('create')"
                     plain>新增
          </el-button>
      </el-row>

      <el-table table-layout="fixed" :data="pageListData" size="small" style="width: 100%">
          <el-table-column prop="id" label="编号"/>
          <el-table-column prop="categoryName" label="分类名称"/>
          <el-table-column prop="sort" label="排序"/>
          <el-table-column prop="createTime" label="创建时间"/>
          <el-table-column label="操作" width="240" align="center">

              <template #default="scope">
                  <el-button size="small" @click="openForm('update', scope.row.id)"
                             v-hasPermission="['articles:category:update']">修改
                  </el-button>
                  <el-button @click="deleteCategory(scope.row)" size="small" type="danger" icon="Delete"
                             v-if="checkPermi(['articles:category:delete'])">
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

      <CategoryForm ref="categoryFormRef" @success="pageList"/>
  </div>
</template>

<style scoped>

</style>