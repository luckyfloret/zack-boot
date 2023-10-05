import {FriendLinkApprovalRejectReqVO, FriendLinkPageReqVO} from "@/api/website/friendlink/types.ts";
import request from "@/utils/request.ts";

export const page = (params: FriendLinkPageReqVO) => {
    return request({
        url: "/website/friend-link/page",
        method: "get",
        params
    })
}

export const getFriendLinkById = (id: number) => {
    return request({
        url: "/website/friend-link/get/" + id,
        method: "get",
    })
}

export const createFriendLink = (data: any) => {
    return request({
        url: "/website/friend-link/create",
        method: "post",
        data
    })
}

export const updateFriendLink = (data: any) => {
    return request({
        url: "/website/friend-link/update",
        method: "put",
        data
    })
}

export const deleteFriendLinkById = (id: number) => {
    return request({
        url: "/website/friend-link/delete/" + id,
        method: "delete",
    })
}

export const approvalPassFriendLink = (id: number) => {
    return request({
        url: "/website/friend-link/approval-pass-friend-link/" + id,
        method: "put",
    })
}

export const approvalRejectFriendLink = (data: FriendLinkApprovalRejectReqVO) => {
    return request({
        url: "/website/friend-link/approval-reject-friend-link",
        method: "put",
        data
    })
}