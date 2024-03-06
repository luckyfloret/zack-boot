import {FileConfigPageReqVO} from "@/api/infra/fileconfig/types.ts";
import request from "@/utils/request.ts";

export const page = (params: FileConfigPageReqVO) => {
    return request({
        url: "/infra/file-config/page",
        method: "get",
        params
    })
}

export const getFileConfigById = (id: number) => {
    return request({
        url: "/infra/file-config/get/" + id,
        method: "get"
    })
}

export const createFileConfig = (data: any) => {
    return request({
        url: "/infra/file-config/create",
        method: "post",
        data
    })
}

export const updateFileConfig = (data: any) => {
    return request({
        url: "/infra/file-config/update",
        method: "put",
        data
    })
}

export const deleteFileConfigById = (id: number) => {
    return request({
        url: "/infra/file-config/delete/" + id,
        method: "delete"
    })
}

export const updateFileConfigMaster = (id: number) => {
    return request({
        url: "/infra/file-config/update-master/" + id,
        method: "put"
    })
}

export const testUpload = (id: number) => {
    return request({
        url: "/infra/file-config/test-upload/" + id,
        method: "get"
    })
}