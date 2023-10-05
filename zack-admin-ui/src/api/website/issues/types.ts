import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseIssuesVO {
    id: number,
    username: string,
    title: string,
    type: number,
    status: number
}

export interface IssuesPageReqVO extends PageParam {
    title?: string,
    type?: number,
    status?: number
}

export interface IssuesPageRespVO extends BaseIssuesVO {
    createTime: Date
}

export interface IssuesRespVO extends BaseIssuesVO {
    content: string
}