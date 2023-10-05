import {defineStore} from "pinia";
import {getAccessToken} from "@/utils/auth.ts";
import {CACHE_KEY} from "@/hooks/useCache.ts";
import {getCache, setCache} from "@/utils/cache.ts";
import {getUserInfo} from "@/api/system/auth";

interface UserInfo {
    permissions: string[]
    roles: string[]
    isCacheUserInfo: boolean
    user: UserDetails
}

interface UserDetails {
    id: number,
    nickname: string,
    avatar: string
}


export const useUserStore = defineStore('user', {
    state: (): UserInfo => ({
        permissions: [],
        roles: [],
        isCacheUserInfo: false,
        user: {
            id: 0,
            nickname: '',
            avatar: ''
        }
    }),
    getters: {
        getPermissions(): string[] {
            return this.permissions
        },
        getRoles(): string[] {
            return this.roles
        },
        getIsCacheUserInfo(): boolean {
            return this.isCacheUserInfo
        },
        getUser(): UserDetails {
            return this.user
        }
    },
    actions: {
        async setUserInfo() {
            if (!getAccessToken()) {
                return null;
            }

            let userInfo = getCache(CACHE_KEY.USER)
            if (!userInfo) {
                let res = await getUserInfo()
                userInfo = res.data.data
            }
            console.log("userInfo => ", userInfo)
            this.permissions = userInfo.permissions
            this.user = userInfo.userVO
            this.isCacheUserInfo = true
            setCache(CACHE_KEY.USER, userInfo)

        }
    },
})