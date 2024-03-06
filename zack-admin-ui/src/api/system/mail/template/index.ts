import request from "@/utils/request.ts";
import {MailTemplatePageReqVO} from "@/api/system/mail/template/types.ts";

export const page = (params: MailTemplatePageReqVO) => {
    return request({
        url: "/system/mail-template/page",
        method: "get",
        params
    })
}
export const getMailTemplateById = (id: number) => {
    return request({
        url: "/system/mail-template/get/" + id,
        method: "get",
    })
}

export const createMailTemplate = (data: any) => {
    return request({
        url: "/system/mail-template/create",
        method: "post",
        data
    })
}

export const updateMailTemplate = (data: any) => {
    return request({
        url: "/system/mail-template/update",
        method: "put",
        data
    })
}

export const deleteMailTemplateById = (id: number) => {
    return request({
        url: "/system/mail-template/delete/" + id,
        method: "delete",
    })
}

export const testSendMail = (data: any) => {
    return request({
        url: "/system/mail-template/test-send-mail",
        method: "post",
        data
    })
}