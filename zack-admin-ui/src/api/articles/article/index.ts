import {ArticlePageReqVO} from "@/api/article/article/types.ts";
import request from "@/utils/request.ts";

export const page = (params: ArticlePageReqVO) => {
    return request({
        url: "/articles/article/page",
        method: "get",
        params
    })
}

export const getArticleById = (id: number) => {
    return request({
        url: "/articles/article/get/" + id,
        method: "get"
    })
}

export const createArticle = (data: any) => {
    return request({
        url: "/articles/article/create",
        method: "post",
        data
    })
}

export const updateArticle = (data: any) => {
    return request({
        url: "/articles/article/update",
        method: "put",
        data
    })
}

export const deleteArticleById = (id: number) => {
    return request({
        url: "/articles/article/delete/" + id,
        method: "delete"
    })
}