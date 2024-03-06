<script setup lang="ts">
import {ElMessageBox, FormInstance} from "element-plus";
import {Ref} from "vue";
import {MailAccountPageReqVO, MailAccountPageRespVO} from "@/api/system/mail/account/types.ts";
import {deleteMailAccountById, page} from "@/api/system/mail/account";
import MailAccountForm from "@/views/system/mail/account/MailAccountForm.vue";

const total = ref<number>(0);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const queryParams: Ref<MailAccountPageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
})

const mailAccountQueryFormRef = ref<FormInstance>()
const pageListData = ref<MailAccountPageRespVO[]>([])
const mailAccountFormRef = ref()
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
    mailAccountFormRef.value.open(type, id)
}

const deleteMailAccount = (row) => {
    ElMessageBox.confirm(`确定要删除用户名为[${row.username}]吗？`,
        "删除",
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
        })
        .then(async () => {
            await deleteMailAccountById(row.id)
            ElNotification.success("删除成功")
            await pageList()
        }).catch(() => {

    })
}
pageList()
</script>

<template>
  <div class="mail-account-container">
      <el-form ref="mailAccountQueryFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
          <el-form-item label="用户名" prop="username">
              <el-input v-model="queryParams.username" placeholder="请输入用户名" style="width: 240px"/>
          </el-form-item>

          <el-form-item label="邮箱" prop="email">
              <el-input v-model="queryParams.email" placeholder="请输入邮箱" style="width: 240px"/>
          </el-form-item>
          <el-form-item>
              <el-button v-hasPermission="['system:mail-account:query']" class="button-global"
                         type="primary" icon="search" @click="handleQuery()">
                  查询
              </el-button>
              <el-button @click="resetForm(mailAccountQueryFormRef)" icon="refresh">
                  重置
              </el-button>
          </el-form-item>
      </el-form>

      <el-row>
          <el-button class="button-global" v-hasPermission="['system:mail-account:create']"
                     size="small" type="primary" icon="plus" @click="openForm('create')"
                     plain>新增
          </el-button>
      </el-row>

      <el-table table-layout="fixed" :data="pageListData" size="small" style="width: 100%">
          <el-table-column prop="id" label="编号"/>
          <el-table-column prop="email" label="邮箱"/>
          <el-table-column prop="username" label="用户名"/>
          <el-table-column prop="password" label="密码"/>
          <el-table-column prop="port" label="SMTP端口"/>
          <el-table-column prop="host" label="SMTP服务器域名"/>
          <el-table-column prop="sslEnable" label="是否开启SSL"/>
          <el-table-column prop="createTime" label="创建时间"/>
          <el-table-column label="操作" width="240" align="center">

              <template #default="scope">
                  <el-button size="small" icon="edit" @click="openForm('update', scope.row.id)"
                             v-hasPermission="['system:mail-account:update']">修改
                  </el-button>
                  <el-button @click="deleteMailAccount(scope.row)" size="small" type="danger" icon="Delete"
                             v-hasPermission="['system:mail-account:delete']">
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

      <MailAccountForm ref="mailAccountFormRef" @success="pageList" />
  </div>
</template>

<style scoped>

</style>