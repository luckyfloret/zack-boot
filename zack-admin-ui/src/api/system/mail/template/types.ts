import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseMailTemplateVO {
    code: string,
    name: string,
    nickname: string,
    accountId: number,
    title: string,
    content: string,
    status: number,
    remark: string
}

export interface MailTemplatePageReqVO extends PageParam {
    name?: string,
    accountId?: number,
    status?: number
    code?: string
}

export interface MailTemplatePageRespVO extends BaseMailTemplateVO {
    id: number,
    createTime: Date
}

export interface MailTemplateRespVO extends BaseMailTemplateVO {
    id: number
}