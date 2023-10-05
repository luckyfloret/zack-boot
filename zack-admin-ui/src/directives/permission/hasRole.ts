import type {App, DirectiveBinding} from 'vue'
import { CACHE_KEY, useCache } from '@/hooks/useCache.ts'


export function hasRole(app: App<Element>) {
  app.directive('hasRole', (el: any, binding: DirectiveBinding) => {
    const { wsCache } = useCache()
    const { value } = binding
    const super_admin = 'admin'
    const roles = wsCache.get(CACHE_KEY.USER).roles

    if (value && value instanceof Array && value.length > 0) {
      const roleFlag = value

      const hasRole = roles.some((role: string) => {
        return super_admin === role || roleFlag.includes(role)
      })

      if (!hasRole) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error("抱歉，当前角色没有权限")
    }
  })
}
