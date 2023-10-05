import type { App } from 'vue'
import { hasRole } from './permission/hasRole'
import {hasPermission} from './permission/hasPermission.ts'

/**
 * 导出指令：v-xxx
 * @methods hasRole 用户权限，用法: v-hasRole
 * @methods hasPermi 按钮权限，用法: v-hasPermi
 */
export const setupAuth = (app: App<Element>) => {
  hasRole(app)
  hasPermission(app)
}
