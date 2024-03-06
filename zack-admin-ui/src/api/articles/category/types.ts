import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseCategoryVO {
    id: number,
    categoryName: string,
    sort: number
}

export interface CategoryPageReqVO extends PageParam {
    categoryName?: string
}

export interface CategoryPageRespVO extends BaseCategoryVO {
    createTime: Date
}

export interface CategoryRespVO extends BaseCategoryVO {

}