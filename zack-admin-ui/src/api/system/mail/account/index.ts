import request from "@/utils/request.ts";
import {MailAccountPageReqVO} from "@/api/system/mail/account/types.ts";

export const page = (params: MailAccountPageReqVO) => {
    return request({
        url: "/system/mail-account/page",
        method: "get",
        params
    })
}

export const getMailAccountById = (id: number) => {
    return request({
        url: "/system/mail-account/get/" + id,
        method: "get",
    })
}

export const createMailAccount = (data: any) => {
    return request({
        url: "/system/mail-account/create",
        method: "post",
        data
    })
}

export const updateMailAccount = (data: any) => {
    return request({
        url: "/system/mail-account/update",
        method: "put",
        data
    })
}

export const deleteMailAccountById = (id: number) => {
    return request({
        url: "/system/mail-account/delete/" + id,
        method: "delete",
    })
}

export const getMailAccountListSimple = () => {
    return request({
        url: "/system/mail-account/list-simple",
        method: "get"
    })
}