<template>
    <el-row :gutter="10" style="height: 60px;  line-height: 60px">
        <el-col :xs="18" :sm="12" :md="12" :lg="12" :xl="12" style="display: flex; align-items: center">
            <div class="left-container">
                <Breadcrumb/>
            </div>
        </el-col>
        <el-col :xs="6" :sm="12" :md="12" :lg="12" :xl="12">
            <div class="right-container">

                <div class="user-info-container">
                    <div class="user-info-wrapper">
                        <img :src="userInfo.avatar"
                             alt="" style="margin-right: 10px">

                        <el-dropdown trigger="click"
                                     style=" cursor: pointer; width: auto; justify-content: end; align-content: flex-end">
                    <span class="el-dropdown-link" style="width: 100px;">
                      {{ userInfo.nickname }}
                        <el-icon class="el-icon--right" style=""><CaretBottom/></el-icon>
                    </span>


                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item @click="userCenter">
                                        个人中心
                                    </el-dropdown-item>
                                    <el-dropdown-item @click="userLogout()">退出登录</el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                    </div>
                </div>
            </div>
        </el-col>
    </el-row>
</template>

<script setup lang="ts">

import Breadcrumb from "@/layout/components/Header/Breadcrumb.vue";
import {logout} from "@/api/login";
import {removeToken} from "@/utils/auth.ts";
import {useRouter} from "vue-router";
import {clearCache, removeCache} from "@/utils/cache.ts";
import {useUserStore} from "@/store/modules/user.ts";
import {ref} from "vue";
import {deleteAllTagView} from "@/utils/tagViewUtil.ts";
import {removeDictDataCache} from "@/utils/dict.ts";
import {CACHE_KEY} from "@/hooks/useCache.ts";
import {resetAllStore} from "@/utils/store.ts";
import {ElLoading} from "element-plus";

const router = useRouter()
const userStore = useUserStore()

const userInfo = ref(userStore.getUser)

const userLogout = async () => {
    await ElMessageBox.confirm(
        '您确定要注销登录吗？',
        '注销',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
    const loading = ElLoading.service({
        lock: true,
        text: '加载中...',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    setTimeout(async () => {
        removeToken();
        clearCache()
        resetAllStore()
        await logout();
        await router.push("/login")

        loading.close()
        ElNotification.success("注销成功")
    }, 500)

}

const userCenter = async () => {
    await router.push("/user-center")
}
</script>

<style scoped>
.left-container {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    /*align-content: center;*/
    align-items: center;
    width: fit-content;
}

.right-container {
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: flex-end;
}

.user-info-wrapper {
    width: auto;
    height: 60px;
    line-height: 60px;
    display: flex;
    align-items: center;
}

.user-info-container :hover {
    /*height: 60px;*/
    /*line-height: 60px;*/
    width: auto;
    background-color: rgb(249, 249, 249);
}

.user-info-wrapper img {
    display: flex;
    align-items: center;
    cursor: pointer;
    width: 35px;
    height: 35px;
    border-radius: 50%;
}

@media screen and (max-width: 540px) {
    .left-container {
        display: none;
    }
}
</style>