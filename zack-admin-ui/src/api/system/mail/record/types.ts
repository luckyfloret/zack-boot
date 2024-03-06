import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseMailRecordVO {
    userId: number,
    userType: number,
    accountId: number,
    toMail: string,
    fromMail: string,
    templateId: number,
    templateCode: string,
    senderNickname: string,
    templateTitle: string,
    templateContent: string,
    templateParams: string,
    sendStatus: number,
    sendTime: Date,
    sendException: string
}

export interface MailRecordPageReqVO extends PageParam {
    userType?: number,
    accountId?: number,
    templateCode?: string,
    sendStatus?: number
}

export interface MailRecordPageRespVO extends BaseMailRecordVO {
    id: number
}

export interface MailRecordRespVO extends BaseMailRecordVO {
    id: number
}