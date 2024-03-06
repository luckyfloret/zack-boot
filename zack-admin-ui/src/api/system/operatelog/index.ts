import {OperateLogPageReqVO} from "@/api/system/operatelog/types.ts";
import request from "@/utils/request.ts";

export const page = (params: OperateLogPageReqVO) => {
    return request({
        url: "/system/operate-log/page",
        method: "get",
        params
    })
}


export const getOperateLogById= (id: number) => {
    return request({
        url: "/system/operate-log/get/" + id,
        method: "get",
    })
}