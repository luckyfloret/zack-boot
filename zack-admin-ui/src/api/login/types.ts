export type UserLoginReqVO = {
    username: string,
    password: string,
    captchaVerification: string
}

export type UserLoginRespVO = {
    userId: number,
    username: string,
    userType: number,
    status: number,
    accessToken: string
    accessTokenExpireTime: Date
}