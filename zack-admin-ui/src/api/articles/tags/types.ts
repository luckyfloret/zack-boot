import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseTagsVO {
    id: number,
    tagName: string,
    sort: number
}

export interface TagsPageReqVO extends PageParam {
    tagName?: string
}

export interface TagsPageRespVO extends BaseTagsVO {
    createTime: Date
}

export interface TagsRespVO extends BaseTagsVO {

}