import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseMailAccountVO {
    email: string,
    username: string,
    password: string,
    port: string,
    host: string,
    sslEnable: boolean
}

export interface MailAccountPageReqVO extends PageParam {
    username?: string,
    email?: string
}

export interface MailAccountPageRespVO extends BaseMailAccountVO {
    id: number,
    createTime: Date
}

export interface MailAccountRespVO extends BaseMailAccountVO {
    id: number
}