export type MenuTreeRespVO = {
    id: number,
    name: string,
    parentId: number,
    path: string,
    icon: string,
    component: string,
    visible: boolean,
    keepAlive: boolean,
    children: Array<MenuTreeRespVO>
}