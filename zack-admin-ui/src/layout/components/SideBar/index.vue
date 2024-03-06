<template>
    <div class="menu-container" style="width: 100%;background-color: rgb(48, 65, 86); ">


        <el-row>
            <el-col >
                <div class="menu" >
                    <Logo :collapse="isCollapse.collapse"/>

                    <el-menu
                        :unique-opened="true"
                            style="height: 100%; width: 100%;"
                            router
                            text-color="#fff"
                            :default-active="activeIndex()"
                            class="el-menu-vertical-demo"
                            :collapse="isCollapse.collapse"
                            @open="handleOpen"
                            @close="handleClose"
                    >


                        <SideBarItem :menuList="menuList"></SideBarItem>
                    </el-menu>
                </div>
            </el-col>
        </el-row>

    </div>
</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue'
import {useRouter} from "vue-router";
import {useAuthStore} from "@/store/modules/auth.ts";
import SideBarItem from "@/layout/components/SideBar/SideBarItem.vue";
import Logo from "@/layout/components/SideBar/Logo.vue";

const isCollapse = defineProps(['collapse'])
const handleOpen = (key, keyPath) => {
    console.log("handleOpen", key, keyPath);
};
const handleClose = (key, keyPath) => {
    console.log(key, keyPath);
};
const router = useRouter();
const authStore = useAuthStore()
//菜单列表
const menuList = reactive([]);

const convert = (menu) => {
    menu.forEach(element => {
        // console.log("element => ", element)
        if (!element.meta && element.children) {
            convert(element.children)
        } else {
            menuList.push(element)
        }
    });
}
convert(authStore.sideBarRoutes)

console.log("menuList => ", menuList)


const hasChildren = item => {
    return item.children && item.children.length;
}

const activeIndex = () => {
    return router.currentRoute.value.meta.activeMenu ? router.currentRoute.value.meta.activeMenu : router.currentRoute.value.fullPath
}
</script>

<style lang="scss">
.menu-container {
  min-height: 100%;

  .el-row {
    height: 100%;

    .el-col {
      .menu {
        height: 100%;
      }
    }
  }

}

.el-menu{
    border-right: none !important;
    background-color: rgb(48, 65, 86) !important;
}
</style>
