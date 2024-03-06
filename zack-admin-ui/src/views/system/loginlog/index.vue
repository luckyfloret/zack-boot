<script lang="ts" setup>
import {DICT_TYPE, getDictDataByDictType} from "@/utils/dict.ts";
import {ref, Ref} from "vue";
import {ElMessageBox, FormInstance} from "element-plus";
import DictTag from "@/components/DictTag/DictTag.vue";
import {LoginLogPageReqVO, LoginLogPageRespVO} from "@/api/system/loginlog/types.ts";
import {page} from "@/api/system/loginlog";
import LoginLogDetails from "@/views/system/loginlog/LoginLogDetails.vue";

const formRef = ref()

const total = ref<number>(0);
const currentPage = ref(1);
const pageSize = ref(10)
const pageSizes = ref<Array<number>>([5, 10, 20, 50, 100, 200, 300, 400])

const queryParams: Ref<LoginLogPageReqVO> = ref({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    username: undefined,
    userType: undefined,
    userIp: undefined,
    result: undefined,
    startTime: undefined,
    endTime: undefined,
    date: undefined
})

const userFormRef = ref<FormInstance>()
const pageListData = ref<LoginLogPageRespVO[]>([])

const resetForm = (formEl: FormInstance | undefined) => {
    console.log("reset form")
    if (!formEl) return
    formEl.resetFields()
    queryParams.value.startTime = undefined
    queryParams.value.endTime = undefined
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

watch([currentPage, pageSize], ([newCurrentPage, newPageSize],[oldCurrentPage, oldPageSize]) => {
    queryParams.value.pageNum = newCurrentPage
    queryParams.value.pageSize = newPageSize
    pageList()
})

const dateFormat = (picker) => {
    queryParams.value.startTime = picker[0]
    queryParams.value.endTime = picker[1]
}

interface Tree {
    label: string
    value: number
    children?: Tree[]
}

const handleNodeClick = (data: Tree) => {
    queryParams.value.userType = undefined
    if (data.value != 0) {
        queryParams.value.userType = data.value
    }
    handleQuery()
}

const userTypeTree: Ref<Tree[]> = ref([])
const initUserTypeTree = () => {
    let userType = {value: 0, label: "zack-blog", children: []}
    userType.children = getDictDataByDictType(DICT_TYPE.USER_TYPE)
    userTypeTree.value.push(userType)
}

initUserTypeTree()

const defaultProps = {
    children: 'children',
    label: 'label',
    value: 'value'
}
const detailsRef = ref()
const openDetails = (id: number) => {
    detailsRef.value.open(id)
}

pageList()
</script>

<template>
    <div class="login-log-container">
        <el-row>
            <el-col :xs="24" :sm="8" :md="5" :lg="3" :xl="3">
                <el-tree empty-text="暂无数据" :data="userTypeTree" default-expand-all :expand-on-click-node="false"
                         :props="defaultProps" @node-click="handleNodeClick"/>
            </el-col>
            <el-col :xs="24" :sm="16" :md="19" :lg="20" :xl="20">
                <el-form ref="userFormRef" :model="queryParams" class="demo-form-inline" :inline="true">
                    <el-form-item label="登录状态" prop="result" style="width: 240px">
                        <el-select
                                placeholder="请选择登录结果"
                                v-model="queryParams.result"
                                clearable
                        >
                            <el-option :key="true" label="成功" :value="true"/>
                            <el-option :key="false" label="失败" :value="false"/>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="用户名称" prop="username">
                        <el-input v-model="queryParams.username" placeholder="请输入用户名称" style="width: 240px"/>
                    </el-form-item>
                    <el-form-item label="登录ip" prop="userIp">
                        <el-input v-model="queryParams.userIp" placeholder="请输入登录ip" style="width: 240px"/>
                    </el-form-item>
                    <el-form-item label="登录时间" prop="date">
                        <el-date-picker
                            v-model="queryParams.date"
                            type="datetimerange"
                            value-format="YYYY-MM-DD HH:mm:ss"
                            range-separator="至"
                            start-placeholder="请选择开始时间"
                            end-placeholder="请选择结束时间"
                            size="default"
                            @change="dateFormat"
                        />
                    </el-form-item>
                    <el-form-item>
                        <el-button v-hasPermission="['system:login-log:query']" class="button-global"
                                   type="primary" icon="search" @click="handleQuery()">
                            查询
                        </el-button>
                        <el-button @click="resetForm(userFormRef)" icon="refresh">
                            重置
                        </el-button>
                    </el-form-item>
                </el-form>

                <el-row>
                    <el-button class="button-global" v-hasPermission="['system:login-log:export']"
                               size="small" type="warning" icon="download">
                        导出
                    </el-button>
                </el-row>

                <el-table table-layout="fixed" :data="pageListData" size="small" style="width: 100%">
                    <el-table-column prop="id" label="编号"/>
                    <el-table-column prop="username" label="用户名"/>
                    <el-table-column prop="loginType" label="登录类型">
                        <template #default="scope">
                            <dict-tag :type="DICT_TYPE.SYSTEM_LOGIN_TYPE" :value="String(scope.row.loginType)"/>
                        </template>
                    </el-table-column>
                    <el-table-column prop="userIp" label="用户ip"/>
                    <el-table-column prop="result" label="登录结果">
                        <template #default="scope">
                            {{scope.row.result == 200 ? "成功" : "失败"}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="登录时间"/>
                    <el-table-column label="操作" width="160" align="center">
                        <template #default="scope">
                            <el-button size="small" type="primary"
                                       v-hasPermission="['system:login-log:query']" @click="openDetails(scope.row.id)">详情
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <el-pagination background prev-text="上一页" next-text="下一页"
                               :page-sizes="pageSizes" :default-page-size="10"
                               :default-current-page="1"
                               v-model::page-size="pageSize"
                               v-model::current-page="currentPage"
                               layout="total, sizes, prev, pager, next, jumper" :total="total" @sizeChange="sizeChange"
                               @currentChange="currentPageChange"
                               style="display: flex; justify-content: flex-end; margin-top: 15px"

                               small
                />

                <LoginLogDetails ref="detailsRef"/>
            </el-col>
        </el-row>
    </div>
</template>

<style scoped>

</style>