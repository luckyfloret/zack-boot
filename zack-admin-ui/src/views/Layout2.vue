<template>

  <!--
    此页面用于解决三级路由及以上菜单重复渲染问题，因为一级路由，二级路由都是目录，他们的组件都是layout
    ，所以三级菜单页面的路由组成部分中，有两个layout，所以会造成页面重复

    路由组件代码实现：如果当前路由是目录并且component 为 null时，会设置当前路由的组件 component 为layout 也就是公共布局组件实现，
    如果当前路由是具体页面并且component 不为 null时，则设置当前路由的 component 为具体页面的 component。

    怎么解决？
    新建一个组件，用于展示视图，超过三级路由及以上时使用，组件设置为当前组件即可

    建议：路由层级不要超过三级
    -->
    <router-view/>
</template>

<script setup lang="ts">
</script>

<style scoped>

</style>