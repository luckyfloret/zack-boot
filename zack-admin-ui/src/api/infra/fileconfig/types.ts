import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseFileConfigVO {
    name: string,
    endpoint: string,
    domain: string,
    bucket: string,
    accessKey: string,
    accessSecret: string,
    remark?: string
}

export interface FileConfigPageRespVO {
    id: number,
    name: string,
    master: boolean,
    createTime: Date,
    remark: string
}

export interface FileConfigPageReqVO extends PageParam {
    name?: string
}

export interface FileConfigRespVO extends BaseFileConfigVO {
    id?: number
}