import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseOperateLogVO {
    username: string,
    module: string,
    name: string,
    type: number,
    result: number,
    operateTime: Date,
    duration: number
}

export interface OperateLogPageRespVO extends BaseOperateLogVO {
    id: number
}

export interface OperateLogRespVO extends BaseOperateLogVO {
    id: number,
    userId: number,
    userType: number,
    requestMethod: string,
    requestUrl: string
    userIp: string,
    userAgent: string,
    javaMethod: string,
    javaMethodArgs: string,
    resultMsg: string,
    resultData: string
}

export interface OperateLogPageReqVO extends PageParam {
    userType?: number,
    module?: string,
    username?: string,
    type?: number,
    result?: boolean,
    startTime?: Date,
    endTime?: Date
}