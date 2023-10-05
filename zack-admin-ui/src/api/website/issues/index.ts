import {IssuesPageReqVO} from "@/api/website/issues/types.ts";
import request from "@/utils/request.ts";

export const page = (params: IssuesPageReqVO) => {
    return request({
        url: "/website/issues/page",
        method: "get",
        params
    })
}

export const getIssuesById = (id: number) => {
    return request({
        url: "/website/issues/get/" + id,
        method: "get",
    })
}

export const updateStatus = (id: number) => {
    return request({
        url: "/website/issues/update-status/" + id,
        method: "put",
    })
}
