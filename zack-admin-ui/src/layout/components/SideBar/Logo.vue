<template>
    <div class="logo-container" style="width: 100%; ">
        <el-collapse-transition :key="isCollapse.collapse">
            <router-link to="/" style="text-decoration: none; display: flex; justify-content: center; color: #e5e6e7">
                <img src="@/assets/vue.svg" alt="" style="">
                <span v-if="!isCollapse.collapse"  style="align-items: center; display: flex ">zack后台管理系统</span>
            </router-link>
        </el-collapse-transition>

    </div>
</template>

<script setup lang="ts">
const isCollapse = defineProps(['collapse'])
</script>

<style scoped>

</style>