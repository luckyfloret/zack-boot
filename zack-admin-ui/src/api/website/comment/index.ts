import request from "@/utils/request.ts";
import {CommentListReqVO} from "@/api/website/comment/types.ts";

export const list = (params: CommentListReqVO) => {
    return request({
        url: "/website/comment/list",
        method: "get",
        params
    })
}

export const getCommentById = (id: number) => {
    return request({
        url: "/website/comment/get/" + id,
        method: "get",
    })
}

export const deleteCommentById = (id: number) => {
    return request({
        url: "/website/comment/delete/" + id,
        method: "delete",
    })
}