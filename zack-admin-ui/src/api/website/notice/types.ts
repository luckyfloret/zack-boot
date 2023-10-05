import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseNoticeVO {
    title: string,
    content: string
}

export interface NoticePageReqVO extends PageParam {
    title?: string,
    type?: number
}

export interface NoticePageRespVO extends BaseNoticeVO {
    id: number,
    type: number,
    status: number,
    createTime: Date
}

export interface NoticeUpdateStatusReqVO {
    id: number,
    type: number,
    status: number
}

export interface NoticeRespVO extends BaseNoticeVO {
    id: number,
    type: number
}