import { CACHE_KEY, useCache } from '@/hooks/useCache'

/**
 * 字符权限校验
 * @param {Array} value 校验值
 * @returns {Boolean}
 */
export function checkPermi(value: string[]) {
  if (value && value instanceof Array && value.length > 0) {
    const { wsCache } = useCache()
    const permissionDatas = value
    const allPermission = '*:*:*'
    const permissions = wsCache.get(CACHE_KEY.USER).permissions
    const hasPermission = permissions.some((permission: string) => {
      return allPermission === permission || permissionDatas.includes(permission)
    })
    return !!hasPermission
  } else {
    console.error("抱歉，您没有权限")
    return false
  }
}

/**
 * 角色权限校验
 * @param {string[]} value 校验值
 * @returns {Boolean}
 */
export function checkRole(value: string[]) {
  if (value && value instanceof Array && value.length > 0) {
    const { wsCache } = useCache()
    const permissionRoles = value
    const super_admin = 'admin'
    const roles = wsCache.get(CACHE_KEY.USER).roles
    const hasRole = roles.some((role: string) => {
      return super_admin === role || permissionRoles.includes(role)
    })
    return !!hasRole
  } else {
    console.error("抱歉，当前角色没有权限")
    return false
  }
}
