import {PageParam} from "@/api/GlobalTypes.ts";

//############################## 字典类型 ##############################
export interface BaseDictTypeVO {
    name: string,
    type: string,
    status: number,
    remark: string
}

export interface DictTypePageReqVO extends PageParam{
    name?: string,
    type?: string,
    status?: number | undefined
}

export interface DictTypePageRespVO extends BaseDictTypeVO{
    id: number,
    createTime: Date,
}

export interface DictTypeRespVO extends BaseDictTypeVO {
    id: number
}

export interface DictTypeListVO {
    id: number,
    name: string,
    type: string
}




//############################## 字典数据 ##############################
export interface BaseDictDataVO {
    sort: number,
    label: string,
    value: string,
    dictType: string,
    status: number,
    colorType: string,
    remark: string
}

export interface DictDataRespVO extends BaseDictDataVO{
    id: number | undefined
}

export interface DictDataPageReqVO extends PageParam{
    dictType?: string | undefined,
    status?: number | undefined
}

export interface DictDataPageRespVO extends BaseDictDataVO{
    id: number
}