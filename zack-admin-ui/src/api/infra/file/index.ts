import {FilePageReqVO} from "@/api/infra/file/types.ts";
import request from "@/utils/request.ts";

export const page = (params: FilePageReqVO) => {
    return request({
        url: "/infra/file/page",
        method: "get",
        params
    })
}

export const deleteFileById = (id: number) => {
    return request({
        url: "/infra/file/delete/" + id,
        method: "delete"
    })
}
