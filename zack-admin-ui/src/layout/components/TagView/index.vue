<script setup lang="ts">
import {Ref, ref, watch} from "vue";
import {deleteAllTagView, deleteTagView, getTagViewList, setTagViewList} from "@/utils/tagViewUtil.ts";
import {TagView} from "@/types/tagView";
import {useAuthStore} from "@/store/modules/auth.ts";
import {useRoute, useRouter} from "vue-router";
import {ElTabPane, ElTabs, TabPaneName, TabsPaneContext} from "element-plus";
import '@/customizer/ElTabPaneCustomize.ts'

const tagList = ref<TagView[]>([])

const router = useRouter()
const route = useRoute()
const currentTabs = ref(route.path)
//初始化tag
const initTagView = () => {
    let tagViewList: TagView[] = getTagViewList();
    if (tagViewList && tagViewList.length) {
        console.log("tagViewList => ", tagViewList)
        tagViewList.forEach(item => tagList.value.push(item))
    } else {
        let routes = router.getRoutes()
        routes.forEach(item => {
            if (item.meta && item.meta.affix) {
                let {path, meta} = item
                let tagView: TagView = {
                    path: path,
                    name: meta.title,
                    affix: meta.affix,
                }
                tagList.value.push(tagView)
                console.log("tagList => ", tagList.value)

            }
        })
    }

}
initTagView();
console.log("init route => ", route.name)
const addTag = () => {
    console.log("addTag => ", route)
    let result = tagList.value.findIndex(item => item.path === route.path) == -1
    if (result) {
        let tag: TagView = {
            path: route.path,
            name: route.meta.title,
            affix: false
        };
        tagList.value.push(tag)
    }


    setTagViewList(tagList.value)
}

const deleteTag = (name: TabPaneName) => {
    console.log("delete Tag => ", name)
    console.log("delete Tag => ", tagList.value[tagList.value.length - 1].path)
    //移除当前标签后设置默认标签
    if (currentTabs.value === name) {
        let tag;
        if (tagList.value[tagList.value.length - 1].path === name) {
            tag = tagList.value[tagList.value.length - 2];
            currentTabs.value = tag.path;
        } else {
            let index = tagList.value.findIndex(item => item.path === name) + 1;
            tag = tagList.value[index];
            currentTabs.value = tag.path;
        }
        router.push(tag.path)
    }

    tagList.value = tagList.value.filter(item => item.path != name);
    setTagViewList(tagList.value)
}

const tabChange = (tabName) => {
    console.log("tagChange => ", tabName)
    currentTabs.value = tabName
    tagList.value.filter(item => item.name === tabName).map(item => router.push(item.path))
}

watch(
    route,
    () => {
        console.log("currentRouter = > ", route.path)
        currentTabs.value = route.path
        addTag()
    },
    {
        deep: true
    }
)
const switchPage = (pane: TabsPaneContext, ev: Event) => {
    router.replace(String(pane.props.name))
}


const removeTab = (targetName: string, type?: string) => {
    console.log("targetName => ", targetName)
    const index = tagList.value.findIndex((item) => item.path === targetName) //查找触发右键菜单所在标签页index
    const currentIndex = tagList.value.findIndex((item) => item.path === currentTabs.value) //查找当前激活标签页index，存在当前激活标签页与触发右键菜单标签页不是同一个的情况
    switch (type) {
        case 'all': //关闭全部标签页
            console.log("all => ")
            tagList.value = tagList.value.filter(item => item.affix)//清空除【首页】外所有标签页
            deleteAllTagView()
            console.log("targetName => ", targetName)
            currentTabs.value = "首页"; //修改标签激活页
            // router.push('/dashboard')
            break
        case 'other': //关闭其他标签页
            console.log("other => ")
            tagList.value = tagList.value.filter((item, i) => item.affix || item.path === targetName)
            if (targetName !== currentTabs.value) {
                currentTabs.value = targetName
            }
            break
        case 'left': //关闭左侧标签页
            console.log("left => ", index)
            tagList.value = tagList.value.filter((item, i) => {
                return !(!item.affix && i < index)
            })
            setTagViewList(tagList.value)
            if (currentIndex < index) {
                currentTabs.value = targetName
            }
            break
        case 'right': //关闭右侧标签页
            console.log("right => ", index)
            for (let i = tagList.value.length - 1; i > index; i--) {
                if (!tagList.value[i].affix) {
                    tagList.value.splice(i, 1)
                }
            }
            setTagViewList(tagList.value)
            if (currentIndex > index) {
                currentTabs.value = targetName
            }
            break
    }
}
const show = (name: string, type: string) => {
    const index = tagList.value.findIndex((item) => name === item.path)
    return type === 'left' ? index !== 0 : index !== tagList.value.length - 1
}


const dropdownRef = ref()
const handleChange = (visible: boolean, name: string) => {
    if (!visible) return
    dropdownRef.value.forEach((item: { id: string; handleClose: () => void }) => {
        if (item.id === name) return
        item.handleClose()
    })
}
</script>


<template>
    <el-tabs
            v-model="currentTabs"
            type="card"
            @tabChange="tabChange"
            class="demo-tabs tag-list"
            @tab-click="switchPage"
            @tab-remove="deleteTag"
    >


        <el-tab-pane
                :closable="!item.affix"
                v-for="item in tagList"
                :key="item.path"
                :label="item.name"
                :name="item.path"
                :path="item.path"
                class="tab-pane"

        >

            <template #label>
                <el-dropdown
                        trigger="contextmenu"
                        :id="item.path"
                        @visible-change="handleChange($event, item.path)"
                        ref="dropdownRef"
                >
                    <span :class="currentTabs === item.path ? 'label' : ''">{{ item.name }}</span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item
                                    @click="removeTab(item.path, 'left')"
                                    v-if="show(item.path, 'left')"
                            >
                                <el-icon>
                                    <DArrowLeft/>
                                </el-icon>
                                关闭左侧标签页
                            </el-dropdown-item>
                            <el-dropdown-item
                                    @click="removeTab(item.path, 'right')"
                                    v-if="show(item.path, 'right')"
                            >
                                <el-icon>
                                    <DArrowRight/>
                                </el-icon>
                                关闭右侧标签页
                            </el-dropdown-item>
                            <el-dropdown-item
                                    @click="removeTab(item.path, 'other')"
                                    v-if="tagList.length > 1"
                            >
                                <el-icon>
                                    <Operation/>
                                </el-icon>
                                关闭其他标签页
                            </el-dropdown-item>
                            <el-dropdown-item @click="removeTab(item.path, 'all')">
                                <el-icon>
                                    <Minus/>
                                </el-icon>
                                关闭全部标签页
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </template>

        </el-tab-pane>

    </el-tabs>
</template>

<style lang="scss" scoped>

:deep(.el-tabs__item) {
  font-size: 12px;
  border: 1px solid #d8dce5 !important;
  border-radius: 3px !important;
  margin-left: 5px;
}

.label {
  color: var(--el-color-primary); //激活标签页高亮
}

:deep(.el-tabs__item) {
  &:hover {
    span {
      color: var(--el-color-primary); //鼠标移到标签页高亮
      transition: 0.2s;
    }
  }

  .el-dropdown {
    line-height: inherit; // 统一标签页显示名称行高
  }
}
</style>