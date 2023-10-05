export interface AuthorInfoVO {
    id: number,
    authorName: string,
    authorSummary?: string,
    authorAvatar?: string,
    aboutAuthor?: string,
    email: string,
    giteeUrl?: string,
    githubUrl?: string
}

export interface WebsiteInfoVO {
    id: number,
    title: string,
    name: string,
    websiteUrl?: string,
    description?: string,
    recordNumber?: string,
    alipay?: string,
    wechatPay?: string,
    isEnabledComment: boolean,
    isEnabledTipping: boolean
}