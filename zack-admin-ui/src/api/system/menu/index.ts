import request from "@/utils/request.ts";

export const menuTree = (params: any) => {
    return request({
        url: "/system/menu/list",
        method: 'get',
        params
    })
}

export const getMenuById = (id: number) => {
    return request({
        url: "/system/menu/get/" + id,
        method: "get",
    })
}

export const createMenu = (menu: any) => {
    return request({
        url: "/system/menu/create",
        method: "post",
        data: menu
    })
}

export const updateMenu = (menu: any) => {
    return request({
        url: "/system/menu/update",
        method: "put",
        data: menu
    })
}

export const deleteMenuById = (id: number) => {
    return request({
        url: "/system/menu/delete/" + id,
        method: "delete"
    })
}