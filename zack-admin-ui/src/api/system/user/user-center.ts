import request from "@/utils/request.ts";

export const getUserInfo = () => {
    return request({
        url: "/system/user-center/getUserInfo",
        method: "get"
    })
}

export const updateUserBasicInfo = (data: any) => {
    return request({
        url: "/system/user-center/update-personal-info",
        method: "put",
        data
    })
}
///update-password

export const updateUserPassword = (data: any) => {
    return request({
        url: "/system/user-center/update-password",
        method: "put",
        data
    })
}