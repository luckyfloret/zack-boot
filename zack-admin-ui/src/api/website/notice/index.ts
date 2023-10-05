import {NoticePageReqVO, NoticeUpdateStatusReqVO} from "@/api/website/notice/types.ts";
import request from "@/utils/request.ts";

export const page = (params: NoticePageReqVO) => {
    return request({
        url: "/website/notice/page",
        method: "get",
        params
    })
}


export const getNoticeById = (id: number) => {
    return request({
        url: "/website/notice/get/" + id,
        method: "get",
    })
}

export const createNotice = (data: any) => {
    return request({
        url: "/website/notice/create",
        method: "post",
        data
    })
}

export const updateNotice = (data: any) => {
    return request({
        url: "/website/notice/update",
        method: "put",
        data
    })
}

export const updateStatus = (data: NoticeUpdateStatusReqVO) => {
    return request({
        url: "/website/notice/update-status",
        method: "put",
        data
    })
}

export const deleteNoticeById = (id: number) => {
    return request({
        url: "/website/notice/delete/" + id,
        method: "delete",
    })
}