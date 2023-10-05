<script setup lang="ts">

import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {CommonStatus} from "@/constants/CommonConstant.ts";
import {checkPermi} from "@/utils/permission.ts";
import {ElMessageBox, FormInstance} from "element-plus";
import {Ref} from "vue";
import {ArticlePageReqVO, ArticlePageRespVO} from "@/api/articles/article/types.ts";
import {deleteArticleById, page} from "@/api/articles/article";
import {listSimple} from "@/api/articles/tags";
import {getSimpleCategoryList} from "@/api/articles/category";


const total = ref<number>(0);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const tagList = ref([])
const categoryList = ref([])
const queryParams: Ref<ArticlePageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    title: undefined,
    tagId: undefined,
    categoryId: undefined,
    isPublish: undefined
})

const articleQueryFormRef = ref<FormInstance>()
const pageListData = ref<ArticlePageRespVO[]>([])
const articleFormRef = ref()
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
    articleFormRef.value.open(type, id)
}

const deleteArticle = (row) => {
    ElMessageBox.confirm(`确定要删除标题为[${row.title}]吗？`,
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
        })
        .then(async () => {
            await deleteArticleById(row.id)
            ElNotification.success("删除成功")
            await pageList()
        }).catch(() => {

    })
}

const initTagList = async () => {
    tagList.value = (await listSimple()).data.data;
}

const initCategoryList = async () => {
    categoryList.value = (await getSimpleCategoryList()).data.data
}
initCategoryList()
initTagList()
pageList()
</script>

<template>
  <div class="article-container">
      <el-form ref="articleQueryFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
          <el-form-item label="标签" prop="tagId" style="width: 240px">
              <el-select
                      placeholder="请选择标签"
                      v-model="queryParams.tagId"
                      clearable
              >
                  <el-option
                          v-for="item in tagList"
                          :key="item.id"
                          :label="item.tagName"
                          :value="item.id"
                  />
              </el-select>
          </el-form-item>
          <el-form-item label="标题" prop="title">
              <el-input v-model="queryParams.title" placeholder="请输入标题" style="width: 240px"/>
          </el-form-item>
          <el-form-item label="分类" prop="categoryId">
              <el-select
                  placeholder="请选择分类"
                  v-model="queryParams.categoryId"
                  clearable
              >
                  <el-option
                      v-for="item in categoryList"
                      :key="item.id"
                      :label="item.categoryName"
                      :value="item.id"
                  />
              </el-select>
          </el-form-item>
          <el-form-item label="发布状态" prop="isPublish">
              <el-select
                  placeholder="请选择发布状态"
                  v-model="queryParams.isPublish"
                  clearable
              >
                  <el-option
                      v-for="item in getDictDataByDictType(DICT_TYPE.ARTICLES_ARTICLE_PUBLISH_STATUS)"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                  />
              </el-select>
          </el-form-item>
          <el-form-item>
              <el-button v-hasPermission="['articles:article:query']" class="button-global"
                         type="primary" icon="search" @click="handleQuery()">
                  查询
              </el-button>
              <el-button @click="resetForm(articleQueryFormRef)" icon="refresh">
                  重置
              </el-button>
          </el-form-item>
      </el-form>

      <el-row>
          <el-button class="button-global" v-hasPermission="['articles:article:create']"
                     size="small" type="primary" icon="plus" @click="openForm('create')"
                     plain>新增
          </el-button>
      </el-row>

      <el-table table-layout="fixed" :data="pageListData" size="small" style="width: 100%">
          <el-table-column prop="id" label="编号"/>
          <el-table-column prop="title" label="标题"/>
          <el-table-column prop="articleCover" label="封面"/>
          <el-table-column prop="readingQuantity" label="阅读量"/>
          <el-table-column prop="tagVOList" label="标签">
              <template #default="scope">
                  <span v-for="(item, index) in scope.row.tagVOList" :key="index">
                      <el-tag style="margin-left: 5px; margin-top: 3px">{{item.tagName}}</el-tag>
                  </span>

              </template>
          </el-table-column>
          <el-table-column prop="category" label="分类">
              <template #default="scope">
                  <el-tag >{{scope.row.categoryName}}</el-tag>
              </template>
          </el-table-column>
          <el-table-column prop="type" label="类型">
              <template #default="scope">
                  <dict-tag :type="DICT_TYPE.ARTICLES_ARTICLE_TYPE" :value="String(scope.row.type)"/>
              </template>
          </el-table-column>
          <el-table-column prop="isPublish" label="发布状态">
              <template #default="scope">
                  <dict-tag :type="DICT_TYPE.ARTICLES_ARTICLE_PUBLISH_STATUS" :value="String(scope.row.isPublish)"/>
              </template>
          </el-table-column>
          <el-table-column prop="isRecommend" label="推荐状态">
              <template #default="scope">
                  <dict-tag :type="DICT_TYPE.ARTICLES_ARTICLE_RECOMMEND_STATUS" :value="String(scope.row.isRecommend)"/>
              </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间"/>
          <el-table-column prop="remark" label="备注"/>
          <el-table-column label="操作" width="240" align="center">

              <template #default="scope">
                  <el-button size="small" @click="openForm('update', scope.row.id)"
                             v-hasPermission="['articles:article:update']">修改
                  </el-button>
                  <el-button @click="deleteArticle(scope.row)" size="small" type="danger" icon="Delete"
                             v-if="checkPermi(['articles:article:delete'])">
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
  </div>
</template>

<style scoped>

</style>