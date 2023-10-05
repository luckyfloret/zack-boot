interface  BaseMenuVO{
    name: string,
    permission: string,
    type: number,
    sort: number,
    parentId: number,
    path: string,
    icon: string,
    component: string,
    status: number,
    visible: boolean,
    keepAlive: boolean
}

export interface MenuListReqVO {
    status: number
}


export interface MenuRespVO extends BaseMenuVO{
    id: number,
    createTime: Date,
    children?: Array<MenuRespVO>
}

export interface MenuCreateReqVO extends BaseMenuVO {

}