import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseRoleVO {
    name: string,
    sort: number,
    code: string,
    status: number,
    remark?: string
}

export interface RolePageReqVO extends PageParam {
    name?: string,
    status?: number
}

export interface RoleCreateReqVO extends BaseRoleVO {

}

export interface RoleUpdateReqVO extends BaseRoleVO {
    id: number
}

export interface RoleReqVO extends BaseRoleVO {
    id: number
}
export interface RolePageRespVO extends BaseRoleVO {
    id: number,
    type: number,
    createTime: Date
}

export interface RoleRespVO{
    id: number,
    name: string,
    code: string,
    status: number,
    sort: number,
    remark: string
}