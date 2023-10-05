import {PageParam} from "@/api/GlobalTypes.ts";

export interface FilePageReqVO extends PageParam {
    filename?: string
}

export interface FilePageRespVO {
    id: number,
    name: string,
    ossFilename: string,
    type: string,
    size: number,
    createTime: Date
}