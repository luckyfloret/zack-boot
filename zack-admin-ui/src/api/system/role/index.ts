import {RoleCreateReqVO, RolePageReqVO, RoleReqVO} from "@/api/system/role/types.ts";
import request from "@/utils/request.ts";

export const page = (params: RolePageReqVO) => {
    return request({
        url: "/system/role/page",
        method: "get",
        params
    })
}

export const createRole = (roleCreateReqVO: RoleCreateReqVO) => {
    return request({
        url: "/system/role/create",
        method: "post",
        data: roleCreateReqVO
    })
}


export const updateRole = (roleUpdateReqVO: RoleReqVO) => {
    return request({
        url: "/system/role/update",
        method: "put",
        data: roleUpdateReqVO
    })
}

export const deleteRole = (id: number) => {
    return request({
        url: "/system/role/delete/" + id,
        method: "delete"
    })
}

export const getRoleById = (id: number) => {
    return request({
        url: "/system/role/get/" + id,
        method: "get"
    })
}