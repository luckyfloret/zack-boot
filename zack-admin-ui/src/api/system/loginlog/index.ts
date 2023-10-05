import {LoginLogPageReqVO} from "@/api/system/loginlog/types.ts";
import request from "@/utils/request.ts";

export const page = (params: LoginLogPageReqVO) => {
    return request({
        url: "/system/login-log/page",
        method: "get",
        params
    })
}


export const getLoginLogById= (id: number) => {
    return request({
        url: "/system/login-log/get/" + id,
        method: "get",
    })
}