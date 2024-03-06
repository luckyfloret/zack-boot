import {getAccessToken} from "@/utils/auth.ts";
import {useUserStore} from "@/store/modules/user.ts";
import {useAuthStore} from "@/store/modules/auth.ts";
import router from "@/router";
import {useDictStore} from "@/store/modules/dict.ts";

router.beforeEach( async (to, from, next) => {
    console.log("from => ", from)
    if (getAccessToken()) {
        if (to.path === '/login' || to.path === "/") {
            next({path: '/'})
        } else {
            const userStore = useUserStore()
            const authStore = useAuthStore()
            const dictStore = useDictStore()

            //缓存字典数据
            if (!dictStore.isCacheDictDataMap) {
                await dictStore.setDictDataMap()
            }

            console.log("before router...")

            //缓存用户信息
            if (!userStore.isCacheUserInfo) {
                await userStore.setUserInfo();

            }

            //缓存菜单信息
            if (!authStore.isCacheMenuTree) {
                await authStore.generateMenuTree();
                next({...to, replace: true});
            }else {
                next()
            }

        }
    } else {
        if (to.path === '/login') {
            next()
        } else {
            next({path: `/login?redirect=${to.fullPath}`});
        }
    }
})