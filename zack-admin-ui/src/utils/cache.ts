import {useCache} from '@/hooks/useCache.ts'

const {wsCache} = useCache()
export const setCache = (key: string, value: any, expire?:number): void => {
    wsCache.set(key, value, {exp: expire})
}

export const getCache = (key:string):any => {
   return wsCache.get(key)
}

export const removeCache = (key: string): void => {
    wsCache.delete(key)
}

export const clearCache = ():void => {
    wsCache.clear()
}