import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseLoginLogVO{
    username: string,
    loginType: number,
    userIp: string,
    userType: number,
    result: number,
    createTime: Date
}

export interface LoginLogPageRespVO extends BaseLoginLogVO{
    id: number,
}

export interface LoginLogRespVO extends BaseLoginLogVO{
    id: number,
    nickname: string
    userAgent: string
}

export interface LoginLogPageReqVO extends PageParam{
    userType?: number,
    userIp?: string,
    username?: string,
    result?: boolean,
    startTime?: Date,
    endTime?: Date
}