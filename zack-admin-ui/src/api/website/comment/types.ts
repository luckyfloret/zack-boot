export interface BaseCommentVO {
    id: number,
    articleName: string,
    username: string,
    replyUsername: string
    parentId: number,
}

export interface CommentTreeListReqVO {
    articleId?: number
}

export interface CommentTreeListRespVO extends BaseCommentVO{
    children?: CommentTreeListRespVO
}

export interface CommentRespVO extends BaseCommentVO{
    content: string
}