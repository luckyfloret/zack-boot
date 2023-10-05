import request from "@/utils/request.ts";
import {PermissionMenuAssignReqVO} from "@/api/system/permission/types.ts";

export const getMenuList = () => {
    return request({
        url: "/system/permission/list-menu",
        method: "get"
    })
}

export const getRolePermission = (roleId: number) => {
    return request({
        url: "/system/permission/get-role-permission/" + roleId,
        method: "get"
    })
}

export const assignMenuPermission = (reqVO: PermissionMenuAssignReqVO) => {
    return request({
        url: "/system/permission/assign-menu-permission",
        method: "post",
        data: reqVO
    })
}

export const getUserRoles = (userId: number) => {
    return request({
        url: "/system/permission/get-user-roles/" + userId,
        method: "get"
    })
}

export const assignUserRole = (data: any) => {
    return request({
        url: "/system/permission/assign-user-role",
        method: "post",
        data
    })
}

export const getRoleList = () => {
    return request({
        url: "/system/permission/list-role",
        method: "get"
    })
}