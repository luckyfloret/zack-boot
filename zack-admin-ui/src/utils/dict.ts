import {CACHE_KEY, useCache} from "@/hooks/useCache.ts";

const {wsCache} = useCache()

export interface DictData {
    dictType: string
    label: string
    value: string | number | boolean
    colorType: "" | "success" | "warning" | "info" | "danger"
}


//##################################### 缓存 #####################################
export const setDictDataCache = (dictDataMap: Map<string, any>): void => {
    wsCache.set(CACHE_KEY.DICT_DATA, Object.fromEntries(dictDataMap))
}

export const getDictDataCache = (): Map<string, any> => {
    return wsCache.get(CACHE_KEY.DICT_DATA) ?
        new Map<string, any>(Object.entries(wsCache.get(CACHE_KEY.DICT_DATA))) : new Map<string, any>()
}

export const removeDictDataCache = (): void => {
    wsCache.delete(CACHE_KEY.DICT_DATA)
}


//##################################### utils  #####################################
export const getDictDataByDictType = (type: string): Array<DictData> => {
    const dictDataMap: Map<string, any> = getDictDataCache()
    return dictDataMap.get(type);
}

export const getDictDataByDictTypeAndValue = (type: string, value: string): Array<DictData> => {
    const dictDataList: Array<DictData> = getDictDataByDictType(type)
    return dictDataList.filter(item => item.value == value)
}


//##################################### dict type  #####################################
export enum DICT_TYPE {
    // ################## common ##################
    COMMON_STATUS = 'common_status',
    USER_TYPE = "user_type",


    // ################## System模块 ##################
    SYSTEM_USER_SEX = "system_user_sex",
    SYSTEM_MENU_TYPE = "system_menu_type",
    SYSTEM_ROLE_TYPE = "system_role_type",
    SYSTEM_LOGIN_TYPE = "system_login_type",
    SYSTEM_OPERATE_TYPE = "system_operate_type",


    // ################## Website模块 ##################
    WEBSITE_FRIENDLINK_STATUS = "website_friendlink_status",
    WEBSITE_NOTICE_TYPE = "website_notice_type",
    WEBSITE_ISSUES_STATUS = "website_issues_status",
    WEBSITE_ISSUES_TYPE = "website_issues_type",


    // ################## infra模块 ##################
    INFRA_FILECONFIG_MASTER = "infra_fileconfig_master",


    // ################## articles模块 ##################
    ARTICLES_ARTICLE_PUBLISH_STATUS = "articles_article_publish_status",
    ARTICLES_ARTICLE_RECOMMEND_STATUS = "articles_article_recommend_status",
    ARTICLES_ARTICLE_TYPE = "articles_article_type",

}