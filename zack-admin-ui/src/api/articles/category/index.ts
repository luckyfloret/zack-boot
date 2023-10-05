import {CategoryPageReqVO} from "@/api/article/category/types.ts";
import request from "@/utils/request.ts";

export const page = (params: CategoryPageReqVO) => {
    return request({
        url: "/articles/category/page",
        method: "get",
        params
    })
}

export const getCategoryById = (id: number) => {
    return request({
        url: "/articles/category/get/" + id,
        method: "get",
    })
}

export const createCategory = (data: any) => {
    return request({
        url: "/articles/category/create",
        method: "post",
        data
    })
}

export const updateCategory = (data: any) => {
    return request({
        url: "/articles/category/update",
        method: "put",
        data
    })
}

export const deleteCategoryById = (id: number) => {
    return request({
        url: "/articles/category/delete/" + id,
        method: "delete",
    })
}

export const getSimpleCategoryList = () => {
    return request({
        url: "/articles/category/list-simple",
        method: "get",
    })
}