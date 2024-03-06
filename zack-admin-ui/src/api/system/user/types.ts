import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseUserVO {
    username: string,
    nickname: string,
    email: string,
    mobile: string,
    sex: number,
    status: number
}

export interface UserPageReqVO extends PageParam{
    username?: string,
    type?: number | undefined,
    email?: string,
    mobile?: string,
    status?: number
}

export interface UserPageRespVO extends BaseUserVO{
    id: number,
    avatar: string,
    loginIp: string,
    loginDate: Date
}


//######################################## 个人中心 ########################################

export interface RoleVO {
    id: number,
    name: string
}
export interface UserCenterVO {
    id: number,
    avatar: string,
    username: string,
    nickname: string,
    email: string,
    mobile: string,
    type: number,
    sex: number,
    roles: Array<RoleVO>,
    status: number
    loginIp: string,
    loginDate: Date,
    createTime: Date
}
