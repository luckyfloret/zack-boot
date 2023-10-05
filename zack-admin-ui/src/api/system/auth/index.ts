import request from "@/utils/request.ts";

export const getMenuTree = () => {
    return request({
        url: '/system/auth/user-menu-nav',
        method: "get"
    })
}

export const getUserInfo = () => {
    return request({
        url: '/system/auth/get-permission',
        method: 'get'
    })
}