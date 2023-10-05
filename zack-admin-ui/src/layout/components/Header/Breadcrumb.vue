<template>
    <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item v-for="(item, index) in route.matched" :key="index">
            <a :href="item.path">{{ item.meta.title}}</a>
        </el-breadcrumb-item>
    </el-breadcrumb>
</template>

<script setup lang="ts">
import {useRoute} from "vue-router";
import {ArrowRight} from "@element-plus/icons-vue";

const route = useRoute()

</script>

<style scoped>

</style>