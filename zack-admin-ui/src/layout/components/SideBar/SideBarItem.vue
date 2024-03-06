<template>
  <!-- 设置判断条件，如果存在子级且有值 -->
    <template v-for="(menu, index) in menuList">
        <el-sub-menu :index="menu.path" v-if="menu.children">
            <template #title>
                <el-icon v-if="menu.meta.icon">
                    <component :is="menu.meta.icon"/>
                </el-icon>
                <span>{{ menu.meta.title }}</span>
            </template>
            <!--                重点：循环调用自己-->
            <SideBarItem :menuList="menu.children"></SideBarItem>
        </el-sub-menu>

        <!-- 设置终止条件，如果没有子级，就不在调用自己 -->
        <el-menu-item :index="menu.path" v-else-if="!menu.meta.hidden && !menu.children">
            <el-icon v-if="menu.meta.icon">
                <component :is="menu.meta.icon"/>
            </el-icon>
            <template #title>
                <span>{{ menu.meta.title }}</span>
            </template>
        </el-menu-item>
    </template>
</template>

<script setup lang="ts">

const {menuList} = defineProps(['menuList'])
</script>

<style>
/*.el-sub-menu .el-menu{*/
/*    background-color: #283847 !important;*/
/*}*/
/*主菜单样式*/
/*>>> .el-submenu__title {*/
/*    background-color: #283847 !important;*/
/*}*/

/*>>>.el-sub-menu__title:hover {*/
/*    background-color: rgba(0, 0, 0, 0.06) !important*/
/*}*/
.el-menu-item:hover {
    background-color: rgba(0, 0, 0, 0.16) !important;
}
</style>

<style scoped>
>>> .el-sub-menu__title:hover {
    background-color: rgba(0, 0, 0, 0.16) !important;
}

>>> .el-sub-menu {
    background-color: #283847 !important;
}

/*子菜单样式*/
>>> .el-menu-item {
    background: #283847 !important;
}

>>>.el-menu-item:hover {
    background-color: rgba(0, 0, 0, 0.3) !important;
}

</style>