<script setup lang="ts">
  import {Ref} from "vue/dist/vue";
  import {ElMessageBox, ElNotification, FormInstance} from "element-plus";
  import {MailTemplatePageReqVO, MailTemplatePageRespVO} from "@/api/system/mail/template/types.ts";
  import {deleteMailTemplateById, page} from "@/api/system/mail/template";
  import {getMailAccountListSimple} from "@/api/system/mail/account";
  import DictTag from "@/components/DictTag/DictTag.vue";
  import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
  import MailTemplateForm from "@/views/system/mail/template/MailTemplateForm.vue";
  import MailTemplateSendForm from "@/views/system/mail/template/MailTemplateSendForm.vue";

  const total = ref<number>(0);
  const currentPage = ref<number>(1);
  const pageSize = ref<number>(10)
  const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

  const mailAccountList = ref([])

  const queryParams: Ref<MailTemplatePageReqVO> = ref({
      pageNum: currentPage.value,
      pageSize: pageSize.value,
  })

  const mailTemplateQueryFormRef = ref<FormInstance>()
  const pageListData = ref<MailTemplatePageRespVO[]>([])
  const mailTemplateFormRef = ref()
  const mailTemplateSendFormRef = ref()
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
      mailTemplateFormRef.value.open(type, id)
  }

  const openMailTemplateSendForm = (id: number) => {
      mailTemplateSendFormRef.value.open(id)
  }

  const deleteMailTemplate = (row) => {
      ElMessageBox.confirm(`确定要删除模板名称为[${row.name}]吗？`,
          "删除",
          {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'error',
          })
          .then(async () => {
              await deleteMailTemplateById(row.id)
              ElNotification.success("删除成功")
              await pageList()
          }).catch(() => {

      })
  }

  const getMailAccountById = (accountId: number) => {
        return mailAccountList.value.filter(item => item.id === accountId)
  }
  const initMailAccountList = async () => {
      mailAccountList.value = (await getMailAccountListSimple()).data.data
  }
  initMailAccountList()
  pageList()
</script>

<template>
  <div class="mail-template-container">
      <el-form ref="mailTemplateQueryFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
          <el-form-item label="模板编码" prop="code">
              <el-input v-model="queryParams.code" placeholder="请输入模板编码" style="width: 240px"/>
          </el-form-item>

          <el-form-item label="模板名称" prop="name">
              <el-input v-model="queryParams.name" placeholder="请输入模板名称" style="width: 240px"/>
          </el-form-item>

          <el-form-item label="状态" prop="status">
              <el-select
                  placeholder="请选择状态"
                  v-model="queryParams.status"
                  clearable
              >
                  <el-option
                      v-for="item in getDictDataByDictType(DICT_TYPE.COMMON_STATUS)"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                  />
              </el-select>
          </el-form-item>


          <el-form-item>
              <el-button v-hasPermission="['system:mail-template:query']" class="button-global"
                         type="primary" icon="search" @click="handleQuery()">
                  查询
              </el-button>
              <el-button @click="resetForm(mailTemplateQueryFormRef)" icon="refresh">
                  重置
              </el-button>
          </el-form-item>
      </el-form>

      <el-row>
          <el-button class="button-global" v-hasPermission="['system:mail-template:create']"
                     size="small" type="primary" icon="plus" @click="openForm('create')"
                     plain>新增
          </el-button>
      </el-row>

      <el-table table-layout="fixed" :data="pageListData" size="small" style="width: 100%">
          <el-table-column prop="id" label="编号"/>
          <el-table-column prop="code" label="模板编码"/>
          <el-table-column prop="name" label="模板名称"/>
          <el-table-column prop="nickname" label="昵称"/>
          <el-table-column prop="accountId" label="邮箱账号">
              <template #default="scope">
                  {{getMailAccountById(scope.row.accountId)[0].email}}
              </template>
          </el-table-column>
          <el-table-column prop="content" label="内容"/>
          <el-table-column prop="status" label="状态">
              <template #default="scope">
                    <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="String(scope.row.status)" />
              </template>
          </el-table-column>
          <el-table-column prop="remark" label="备注"/>
          <el-table-column prop="createTime" label="创建时间"/>
          <el-table-column label="操作" width="240" align="center">

              <template #default="scope">
                  <el-button size="small" @click="openMailTemplateSendForm(scope.row.id)"
                             v-hasPermission="['system:mail-template:test-send']">测试
                  </el-button>

                  <el-button size="small" icon="edit" @click="openForm('update', scope.row.id)"
                             v-hasPermission="['system:mail-template:update']">修改
                  </el-button>
                  <el-button @click="deleteMailTemplate(scope.row)" size="small" type="danger" icon="Delete"
                             v-hasPermission="['system:mail-template:delete']">
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

      <MailTemplateForm ref="mailTemplateFormRef" @success="pageList" :mailAccountList="mailAccountList" />

      <MailTemplateSendForm ref="mailTemplateSendFormRef"/>

  </div>
</template>



<style scoped>

</style>