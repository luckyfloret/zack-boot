<template>
  <span>
    <template v-for="(dict, index) in getDictDataByDictTypeAndValue(<string>type, <string>value)">
      <!-- 默认样式 -->
      <span v-if="dict.colorType === 'default' || dict.colorType === ''"
            :key="dict.value">{{ dict.label }}</span>


        <!-- tag的默认样式，使用primary标识 -->
        <el-tag v-else-if="dict.colorType === 'primary'" :disable-transitions="true" :key="dict.value" :index="index"
                :type="''">
          {{ dict.label }}
        </el-tag>
        <!-- Tag 样式 -->
      <el-tag v-else :disable-transitions="true" :key="dict.value" :index="index" :type="dict.colorType">
        {{ dict.label }}
      </el-tag>
    </template>
  </span>
</template>

<script setup lang="ts">

import {getDictDataByDictTypeAndValue} from "@/utils/dict.ts";

const {type, value} = defineProps({type: String, value: String})

</script>
<style scoped>
.el-tag + .el-tag {
    margin-left: 10px;
}
</style>
