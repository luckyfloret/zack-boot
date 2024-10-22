// typings.d.ts or router.ts
import 'vue-router'

declare module 'vue-router' {
    interface RouteMeta {
        title:string,
        icon?:string,
        affix?: boolean,
        activeMenu?: string
    }
}