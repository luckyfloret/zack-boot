import {DictTypePageReqVO} from "@/api/system/dict/types.ts";
import request from "@/utils/request.ts";

export const page = (params: DictTypePageReqVO) => {
    return request({
        url: "/system/dict-type/page",
        method: "get",
        params
    })
}

export const getById = (id: number) => {
    return request({
        url: "/system/dict-type/get/" + id,
        method: "get"
    })
}

export const createDictType = (data: any) => {
    return request({
        url: "/system/dict-type/create",
        method: "post",
        data
    })
}

export const updateDictType = (data: any) => {
    return request({
        url: "/system/dict-type/update",
        method: "put",
        data
    })
}

export const deleteDictType = (id: number) => {
    return request({
        url: "/system/dict-type/delete/" + id,
        method: "delete"
    })
}

export const getDictTypeList = () => {
    return request({
        url: "/system/dict-type/list",
        method: "get"
    })
}