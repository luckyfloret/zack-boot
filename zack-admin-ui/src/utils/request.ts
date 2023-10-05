import axios, {AxiosResponse} from "axios";
import {getAccessToken, getRefreshToken, removeToken, setToken} from "@/utils/auth.ts";
import {refreshToken} from "@/api/login";
import {ElLoading, ElNotification} from "element-plus";
import router from "@/router";
import {clearCache} from "@/utils/cache.ts";
import {resetAllStore} from "@/utils/store.ts";

const instance = axios.create({
    // baseURL: 'http://localhost:11000/admin',
    baseURL: import.meta.env.VITE_BASE_API_URL ,
    timeout: 3000,
    headers: {'X-Custom-Header': 'foobar'}
});

// 添加请求拦截器
instance.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    console.log("before request...")
    if (getAccessToken()) {
        config.headers['Authorization'] = "Bearer " + getAccessToken();
    }

    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 是否正在刷新的标记
let isRefreshing = false
// 重试队列，每一项将是一个待执行的函数形式
let requests: any[] = []

instance.interceptors.response.use(async (response: AxiosResponse<any, any>): Promise<any> => {
    if (!response.data) {
        return Promise.reject(new Error())
    }

    const code = response.data.code | 200

    const {message} = response.data
    console.log("response code => ", code)

    if (code === 10200004 || code === 10200005) {
        await ElMessageBox.alert(
            "令牌已过期，请重新登录",
            "重新登陆",
            {
                confirmButtonText: '重新登陆',
                type: 'warning',
            }
        )
        const loading = ElLoading.service({
            lock: true,
            text: '加载中...',
            background: 'rgba(0, 0, 0, 0.7)',
        })
        removeToken()
        clearCache()
        resetAllStore()
        await router.push('/login')
        loading.close()
        return Promise.reject(new Error())
    }

    if (code === 401) {
        const config = response.config;
        if (!isRefreshing) {
            try {
                if (!getRefreshToken()) {
                    removeToken()
                    clearCache()
                    resetAllStore()
                    await router.push("/login")
                    ElNotification.error("令牌已过期")
                    return Promise.reject();
                } else {
                    isRefreshing = true
                    const res = await refreshToken(getRefreshToken())
                    const data = res.data.data
                    setToken(data.accessToken, data.refreshToken)
                    config.headers['Authorization'] = "Bearer " + data.accessToken
                    // 已经刷新了token，将所有队列中的请求进行重试
                    requests.forEach(cb => cb(data.accessToken))
                    // requests = []
                    return instance(config)
                }
            } finally {
                requests = []
                isRefreshing = false
            }

        } else {
            // 正在刷新token，将返回一个未执行resolve的promise
            return new Promise((resolve) => {
                // 将resolve放进队列，用一个函数形式来保存，等token刷新后直接执行
                requests.push(() => {
                    config.headers['Authorization'] = "Bearer " + getAccessToken()
                    resolve(instance(config))
                })
            })
        }
    } else if (code === 500) {
        ElNotification.error(message)
    }

    if (code != "0000" && code != 200) {
        console.log("error => ", code)
        ElNotification.error(message)
        return Promise.reject(new Error());
    }
    return response;

}, error => {
    const {message} = error
    ElNotification.error(message)
    return Promise.reject(error)
});

export default instance;
