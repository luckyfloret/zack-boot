import {defineStore} from "pinia";
import {dictDataList, DictDataListRespVO} from "@/api/system/dict/dict-data.ts";
import {getDictDataCache, setDictDataCache} from "@/utils/dict.ts";

export const useDictStore = defineStore('dict', {
    state: () => ({
        dictDataMap: new Map<string, any>(),
        isCacheDictDataMap: false
    }),
    actions: {
        async setDictDataMap() {
            let dictDataMapCache: Map<string, any> = getDictDataCache()
            if (!dictDataMapCache.size || !dictDataMapCache) {
                let {data} = await dictDataList()
                let dictDataMap = new Map<string, any>()
                dictDataMap = data.data.reduce((acc: any, current: DictDataListRespVO) => {
                    const {dictType, label, value, colorType} = current
                    const dictData = {
                        label,
                        value,
                        colorType
                    }
                    if (acc.has(dictType)) {
                        acc.get(dictType)?.push(dictData)
                    } else {
                        acc.set(dictType, [dictData])
                    }
                    return acc
                }, new Map<string, any>())
                this.dictDataMap = dictDataMap
                this.isCacheDictDataMap = true
                setDictDataCache(dictDataMap)
            } else {
                this.dictDataMap = dictDataMapCache
                this.isCacheDictDataMap = true
            }
        }
    },
})