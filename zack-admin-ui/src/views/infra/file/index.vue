<script setup lang="ts">
import {checkPermi} from "@/utils/permission.ts";
import {ElMessageBox, FormInstance} from "element-plus";
import {Ref} from "vue";
import {FilePageReqVO, FilePageRespVO} from "@/api/infra/file/types.ts";
import {deleteFileById, page} from "@/api/infra/file";
import FileForm from "@/views/infra/file/FileForm.vue";

const total = ref<number>(0);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const queryParams: Ref<FilePageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    filename: undefined,
})

const fileQueryFormRef = ref<FormInstance>()
const pageListData = ref<FilePageRespVO[]>([])
const fileFormRef = ref()
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

const openForm = () => {
    fileFormRef.value.open()
}

const deleteFile = (row) => {
    ElMessageBox.confirm(`确定要删除文件名为[${row.name}]吗？`,
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
        })
        .then(async () => {
            await deleteFileById(row.id)
            ElNotification.success("删除成功")
            await pageList()
        }).catch(() => {

    })
}

pageList()
</script>

<template>
  <div class="file-container">
      <el-form ref="fileQueryFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
          <el-form-item label="文件名" prop="filename">
              <el-input v-model="queryParams.filename" placeholder="请输入文件名" style="width: 240px"/>
          </el-form-item>
          <el-form-item>
              <el-button v-hasPermission="['infra:file:query']" class="button-global"
                         type="primary" icon="search" @click="handleQuery()">
                  查询
              </el-button>
              <el-button @click="resetForm(fileQueryFormRef)" icon="refresh">
                  重置
              </el-button>
          </el-form-item>
      </el-form>

      <el-row>
          <el-button class="button-global" v-hasPermission="['infra:file:upload']"
                     size="small" type="primary" icon="plus" @click="openForm()"
                     plain>上传文件
          </el-button>
      </el-row>

      <el-table table-layout="fixed" :data="pageListData" size="small" :show-overflow-tooltip="true" style="width: 100%">
          <el-table-column prop="id" label="编号"/>
          <el-table-column prop="name" label="文件名"/>
          <el-table-column prop="ossFilename" label="oss文件名"/>
          <el-table-column prop="url" label="url"/>
          <el-table-column prop="type" label="类型"/>
          <el-table-column prop="size" label="大小"/>
          <el-table-column prop="createTime" label="上传时间"/>
          <el-table-column label="操作" width="240" align="center">

              <template #default="scope">
                  <el-button @click="deleteFile(scope.row)" size="small" type="danger" icon="Delete"
                             v-if="checkPermi(['infra:file:delete'])">
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

      <FileForm ref="fileFormRef" @success="pageList" />
  </div>
</template>

<style scoped>

</style>