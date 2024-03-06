import {UserPageReqVO} from "@/api/system/user/types.ts";
import request from "@/utils/request.ts";

export const page = (params: UserPageReqVO) => {
    return request({
        url: "/system/user/page",
        method: "get",
        params
    })
}

export const createUser = (data: any) => {
    return request({
        url: "/system/user/create",
        method: "post",
        data
    })
}

export const updateUser = (data: any) => {
    return request({
        url: "/system/user/update",
        method: "put",
        data
    })
}

export const deleteUser = (id: number) => {
    return request({
        url: "/system/user/delete/" + id,
        method: "delete"
    })
}

export const getUserById = (id: number) => {
    return request({
        url: "/system/user/get/" + id,
        method: "get"
    })
}

export const resetPassword = (data: any) => {
    return request({
        url: "/system/user/reset-password",
        method: "put",
        data
    })
}

