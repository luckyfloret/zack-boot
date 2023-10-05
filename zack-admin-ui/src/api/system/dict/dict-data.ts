import request from "@/utils/request.ts";
import {DictDataPageReqVO} from "@/api/system/dict/types.ts";

export type DictDataListRespVO = {
    dictType: string,
    label: string,
    value: string,
    colorType: string
}

export const dictDataList = () => {
    return request({
        url: '/system/dict-data/list',
        method: 'get'
    })
}

export const page = (params: DictDataPageReqVO) => {
    return request({
        url: "/system/dict-data/page",
        method: "get",
        params
    })
}
export const getDictDataById = (id: number) => {
    return request({
        url: "/system/dict-data/get/" + id,
        method: "get"
    })
}

export const createDictData = (data: any) => {
    return request({
        url: "/system/dict-data/create",
        method: "post",
        data
    })
}

export const updateDictData = (data: any) => {
    return request({
        url: "/system/dict-data/update",
        method: "put",
        data
    })
}

export const deleteDictDataById = (id: number) => {
    return request({
        url: "/system/dict-data/delete/" + id,
        method: "delete"
    })
}