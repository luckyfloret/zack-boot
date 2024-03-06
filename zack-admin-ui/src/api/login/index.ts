import request from "@/utils/request.ts";

export const login = (data: any) => {
    return request({
        url: '/system/auth/login',
        method: "POST",
        data
    })
}

export const logout = () => {
    return request({
        url: '/system/auth/logout',
        method: 'post',
    })
}

export const refreshToken = (data: string | null) => {
    return request.post('/system/auth/refresh-token?refreshToken=' + data)
}

export const getCodeApi = (data: string): any => {
    return request({
        url: "/captcha/get",
        method: 'POST',
        data: data
    })
}

export const reqCheckApi = (data: any) => {
    return request({
        url: "/captcha/check",
        method: "POST",
        data: data
    })
}