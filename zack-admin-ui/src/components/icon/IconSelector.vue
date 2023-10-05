<script lang="ts" setup>
import {ref} from 'vue'
import * as Icons from '@element-plus/icons-vue'

const dialogVisible = ref(false)

const icons = ref([])
const init = () => {
    for (const name in Icons) {
        icons.value.push(name)
    }
}

const open = () => {
    dialogVisible.value = true
    icons.value = []
    init()
}
const emit = defineEmits(['iconSelector'])

const selectIcon = (iconName) => {
    console.log("iconName => ", iconName)
    emit("iconSelector", iconName)
    dialogVisible.value = false
}

defineExpose({open})
</script>

<template>
    <el-dialog
            v-model="dialogVisible"
            title="请选择图标"
            width="30%"
            style="height: 50%; overflow: auto"
            draggable
    >
        <div>
            <span v-for="(iconName, index) in icons" :key="index" @click="selectIcon(iconName)">
                <component :is="iconName" style="width: 20px; height: 20px">
                </component>
                {{iconName}}
            </span>
        </div>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          确定
        </el-button>
      </span>
        </template>
    </el-dialog>
</template>


<style scoped>
.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>
