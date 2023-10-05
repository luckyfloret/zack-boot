import request from "@/utils/request.ts";
import {AuthorInfoVO, WebsiteInfoVO} from "@/api/website/webconfig/types.ts";

export const getWebsiteInfo = () => {
    return request({
        url: "/website/web-config/get-website-info",
        method: "get"
    })
}

export const getAuthorInfo = () => {
    return request({
        url: "/website/web-config/get-author-info",
        method: "get"
    })
}

export const updateWebsiteInfo = (data: WebsiteInfoVO) => {
    return request({
        url: "/website/web-config/update-website-info",
        method: "put",
        data
    })
}

export const updateAuthorInfo = (data: AuthorInfoVO) => {
    return request({
        url: "/website/web-config/update-author-info",
        method: "put",
        data
    })
}