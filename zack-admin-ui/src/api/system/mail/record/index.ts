import request from "@/utils/request.ts";
import {MailRecordPageReqVO} from "@/api/system/mail/record/types.ts";

export const page = (params: MailRecordPageReqVO) => {
    return request({
        url: "/system/mail-record/page",
        method: "get",
        params
    })
}

export const getMailRecordById = (id: number) => {
    return request({
        url: "/system/mail-record/get/" + id,
        method: "get"
    })
}