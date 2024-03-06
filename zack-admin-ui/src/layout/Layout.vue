<template>
    <div class="common-layout">
        <el-container class="layout-container">
            <el-aside style="overflow: hidden; transition: 0.5s;" :width="isCollapse ? '64px':'200px'">
                <SideBar :collapse="isCollapse"/>
            </el-aside>
            <el-container>
                <el-header class="header-container">

                    <el-row :gutter="10" align="middle">
                        <el-col :xs="3" :sm="2" :md="2" :lg="1" :xl="1">
                            <div class="expand-icon">
                                <el-radio-group v-model="isCollapse" style="width: 40px; height: 40px">
                                    <el-icon v-show="isCollapse" @click="isCollapse=false" style="font-size:30px;">
                                        <Fold/>
                                    </el-icon>
                                    <el-icon v-show="!isCollapse" @click="isCollapse=true" style="font-size:30px;">
                                        <Expand/>
                                    </el-icon>
                                </el-radio-group>
                            </div>
                        </el-col>
                        <el-col :xs="21" :sm="22" :md="22" :lg="23" :xl="23">
                            <Header class="header-element"/>
                        </el-col>
                    </el-row>

                </el-header>

                <el-row class="tag-view-container">
                    <TagView></TagView>
                </el-row>
                <el-main>
                    <router-view/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script setup lang="ts">
import SideBar from "@/layout/components/SideBar/index.vue";
import {Expand, Fold} from "@element-plus/icons-vue";
import {onBeforeUnmount, ref, watch} from "vue";
import Header from '@/layout/components/Header/index.vue'
import TagView from '@/layout/components/TagView/index.vue'

const isCollapse = ref(false)
const screenWidth = ref(window.innerWidth);

const onResize = () => {
    screenWidth.value = window.innerWidth;
    // 在此处执行其他逻辑
    isCollapse.value = screenWidth.value < 540;
};

watch(
    () => screenWidth.value,
    onResize,
    {immediate: true}
);

//添加一个resize事件监听器来监测屏幕宽度的变化
window.addEventListener('resize', onResize);

//在beforeUnmount生命周期钩子中，我们移除了该事件监听器，以防止内存泄漏。
onBeforeUnmount(() => {
    window.removeEventListener('resize', onResize);
})

</script>

<style lang="scss">
.common-layout {
  width: 100vw;
  height: 100vh;

  .layout-container {
    width: 100%;
    height: 100%;

    .header-container {
      width: 100%;
      line-height: 60px;
      border-bottom: 1px solid var(--el-border-color);

      .expand-icon {
        cursor: pointer;
        display: flex;
      }

      .expand-icon :hover {
        background-color: var(--el-menu-hover-bg-color);
      }

      .header-element {
        width: 100%;
      }
    }
  }
}

.tag-view-container {
  height: 40px;
  border-bottom: 1px solid var(--el-border-color);
}
@media screen and (max-width: 400px) {
    .el-header{
        --el-header-padding: 0 !important;
    }
}
</style>