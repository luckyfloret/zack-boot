import {defineStore} from 'pinia'
import {MenuTreeRespVO} from "@/api/system/auth/types.ts";
import router from "@/router";
import {convertPath, reduceFields} from "@/utils/auth.ts";
import {RouteRecordRaw} from "vue-router";
import {getCache, setCache} from "@/utils/cache.ts";
import {CACHE_KEY} from "@/hooks/useCache.ts";
import {getMenuTree} from "@/api/system/auth";

export const useAuthStore = defineStore('auth', {
    // persist: {
    //     key: "menuTree",
    // },
    state: () => {
        return {
            menuTree: [],
            sideBarRoutes: [],
            isCacheMenuTree: false
        }
    },
    // 也可以定义为
    actions: {
        setMenuTree(menuTree: MenuTreeRespVO): void {
            this.menuTree = menuTree;
        },

        setSideBarRoutes(routes: Array<any>) {
            routes.forEach(route => this.sideBarRoutes.push(route))
        },

        async generateMenuTree() {
            let menuTree = getCache(CACHE_KEY.MENU_TREE);
            if (!menuTree) {
                let res = (await getMenuTree()).data;
                menuTree = res.data
                console.log("menuTree => ", menuTree)
                setCache(CACHE_KEY.MENU_TREE, menuTree)
            }

            //添加路由
            //减少字段
            const convertMenuTree: Array<RouteRecordRaw> = reduceFields(menuTree)
            const finalMenuTree: Array<RouteRecordRaw> = convertPath(convertMenuTree);
            console.log("convertMenuTree => ", convertMenuTree)
            console.log("finalMenuTree => ", finalMenuTree[0])
            finalMenuTree.forEach(route => {
                router.addRoute(route)
            })

            //通过这种方式获取静态路由+动态路由实属无奈之举（前端菜鸡），为什么不用getRoutes()？，因为它获取的是所有的路由记录，完整的路由列表
            this.setSideBarRoutes(router.options.routes)
            this.setSideBarRoutes(finalMenuTree)
            this.setMenuTree(menuTree);
            console.log("sideBarRoutes => ", this.sideBarRoutes)
            this.isCacheMenuTree = true
        },

    },
})