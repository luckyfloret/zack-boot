import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router";
import Layout from "@/layout/Layout.vue";

// 2. 定义一些路由
// 每个路由都需要映射到一个组件。
// 我们后面再讨论嵌套路由。
const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        redirect: '/dashboard',
        component: Layout,
        children: [
            {
                path: '/dashboard',
                name: 'index',
                component: () => import('@/views/index.vue'),
                meta:{
                    icon: "HomeFilled",
                    title: "首页",
                    affix: true
                }
            },
            {
                path: '/system/dict/data/:dictId',
                name: 'DictData',
                component: () => import('@/views/system/dict/data/index.vue'),
                meta: {
                    hidden: true,
                    title: "字典数据",
                    activeMenu: '/system/dict'
                }
            },
            {
                path: '/user-center',
                name: 'UserCenter',
                component: () => import('@/views/system/user/center/index.vue'),
                meta: {
                    hidden: true,
                    title: "个人中心",
                }
            }
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/login/index.vue'),
        meta: {
            hidden: true,
            title: "账号密码登录"
        }
    },

    {
        // 404页面配置 /:catchAll(.*)
        path: '/:catchAll(.*)',
        component: () => import('../views/error/404.vue'),
        meta:{
            hidden: true,
            title: "404"
        }
    }

]

// 3. 创建路由实例并传递 `routes` 配置
// 你可以在这里输入更多的配置，但我们在这里
// 暂时保持简单
const router = createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: createWebHistory(),
    routes, // `routes: routes` 的缩写
})
export default router;