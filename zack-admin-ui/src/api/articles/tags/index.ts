import request from "@/utils/request.ts";
import {TagsPageReqVO} from "@/api/article/tags/types.ts";

export const page = (params: TagsPageReqVO) => {
    return request({
        url: "/articles/tags/page",
        method: "get",
        params
    })
}

export const getTagsById = (id: number) => {
    return request({
        url: "/articles/tags/get/" + id,
        method: "get",
    })
}

export const createTags = (data: any) => {
    return request({
        url: "/articles/tags/create",
        method: "post",
        data
    })
}

export const updateTags = (data: any) => {
    return request({
        url: "/articles/tags/update",
        method: "put",
        data
    })
}

export const deleteTagsById = (id: number) => {
    return request({
        url: "/articles/tags/delete/" + id,
        method: "delete",
    })
}

export const listSimple = () => {
    return request({
        url: "/articles/tags/list-simple",
        method: "get",
    })
}