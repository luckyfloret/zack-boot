import {useAuthStore} from "@/store/modules/auth.ts";
import {useUserStore} from "@/store/modules/user.ts";
import {useDictStore} from "@/store/modules/dict.ts";

export const resetAuthStore = () => {
    useAuthStore().$reset()
}

export const resetUserStore = () => {
    useUserStore().$reset()
}

export const resetDictStore = () => {
    useDictStore().$reset()
}

export const resetAllStore = () => {
    useAuthStore().$reset()
    useUserStore().$reset()
    useDictStore().$reset()
}