import type {App, DirectiveBinding} from 'vue'
import { CACHE_KEY, useCache } from '@/hooks/useCache'

export function hasPermission(app: App<Element>) {
  app.directive('hasPermission', (el: any, binding: DirectiveBinding<any>) => {
    const { wsCache } = useCache()
    const { value } = binding
    const all_permission = '*:*:*'
    const permissions = wsCache.get(CACHE_KEY.USER).permissions
    if (value && value instanceof Array && value.length > 0) {
      const permissionFlag = value
      const hasPermissions = permissions.some((permission: string) => {
        return all_permission === permission || permissionFlag.includes(permission)
      })
      if (!hasPermissions) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error("抱歉，您没有权限")
    }
  })
}
