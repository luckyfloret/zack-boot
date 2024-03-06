import {PageParam} from "@/api/GlobalTypes.ts";

export interface BaseFriendLinkVO {
    name: string,
    description: string,
    avatarUrl: string,
    email: string,
    websiteUrl: string,
    sort: number
}

export interface FriendLinkPageReqVO extends PageParam {
    name?: string,
    email?: string,
    approvalStatus?: number
}

export interface FriendLinkPageRespVO extends BaseFriendLinkVO {
    id: number,
    approvalStatus: number,
    createTime: Date
}

export interface FriendLinkApprovalRejectReqVO {
    id: number,
    rejectOpinion: string
}