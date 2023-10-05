//##################################### constants #####################################
import {RouteRecordRaw} from "vue-router";

const ACCESS_TOKEN: string = "access_token"
const REFRESH_TOKEN: string = "refresh_token"


//##################################### Method #####################################
export const setToken = (accessToken: string, refreshToken: string): void => {
    localStorage.setItem(ACCESS_TOKEN, accessToken)
    localStorage.setItem(REFRESH_TOKEN, refreshToken)
}

export const getAccessToken = (): string | null => {
    return localStorage.getItem(ACCESS_TOKEN)
}

export const getRefreshToken = (): string | null => {
    return localStorage.getItem(REFRESH_TOKEN)
}

export const removeToken = (): void => {
    localStorage.removeItem(ACCESS_TOKEN)
    localStorage.removeItem(REFRESH_TOKEN)
}


const modules = import.meta.glob('../views/**/*.vue')  // 导入

import Layout from "@/layout/Layout.vue";

export const reduceFields = (menuTree: Array<any>): Array<RouteRecordRaw> => {
    return menuTree.map(item => {
        let {path, name, visible, component, icon, children} = item; // 仅保留需要的字段
        if (component && component.length) {
            component = modules[`../views/${component}.vue`]
        } else {
            component = Layout
        }
        const meta = {
            title: name,
            icon,
            hidden: !visible
        }
        const reducedItem = {path, name, component, icon, meta, children};

        if (children && children.length) {
            reducedItem.children = reduceFields(children);
        }

        return reducedItem;
    });
}

export const convertPath = (menuTree: Array<RouteRecordRaw>, parentPath = '') => {
    return menuTree.map(menu => {
        menu.path = parentPath + menu.path
        if (menu.children && menu.children.length) {
            convertPath(menu.children, menu.path)
        }
        return menu
    })
}