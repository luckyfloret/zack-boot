import WebStorageCache from 'web-storage-cache'
export const CACHE_KEY = {
    USER: "user",
    DICT_DATA: 'dict-data',
    MENU_TREE: "menu-tree"
}
type CacheType = 'localStorage' | 'sessionStorage'
export const useCache = (type: CacheType = 'localStorage') => {
    const wsCache: WebStorageCache = new WebStorageCache({
        storage: type
    })

    return {
        wsCache
    }
}