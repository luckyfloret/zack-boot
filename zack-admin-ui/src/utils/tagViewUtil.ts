import {TagView} from "@/types/tagView";
import {useCache} from "@/hooks/useCache.ts";

const TAG_VIEW_KEY = "zack-tag-view"

const {wsCache} = useCache()

export const setTagViewList = (tagView: Array<TagView>): void => {
    wsCache.set(TAG_VIEW_KEY, tagView)
}


export const getTagViewList = (): Array<TagView> => {
    return wsCache.get(TAG_VIEW_KEY) ? wsCache.get(TAG_VIEW_KEY) : []
}

export const deleteAllTagView = (): void => {
    wsCache.delete(TAG_VIEW_KEY)
}

export const deleteTagView = (tagViewName: string): void => {
    let tagViewList: TagView[] = getTagViewList();
    let newTagViewList:TagView[] = tagViewList.filter(item => item.name != tagViewName)
    setTagViewList(newTagViewList)
}