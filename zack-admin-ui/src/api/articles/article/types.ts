import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseArticleVO {
    articleCover: string,
    title: string,
    reprintUrl: string,
    summary: string,
    contentMd: string,
    contentHtml: string,
    type: number,
    isRecommend: number,
    isPublish: number,
    remark: string
}

export interface ArticlePageRespVO {
    id: number,
    articleCover: string,
    title: string,
    type: number,
    isRecommend: number,
    isPublish: number,
    readingQuantity: string,
    categoryId: number,
    categoryName: string,
    tagVOList: TagVO[],
    createTime: Date,
    remark: string
}

export interface TagVO {
    id: number,
    tagName: string
}

export interface ArticlePageReqVO extends PageParam{
    title?: string,
    tagId?: number,
    categoryId?: number,
    isPublish?: number
}

export interface ArticleRespVO extends BaseArticleVO {
    id: number,
    categoryName: string,
    tagVOList: TagVO[]
}