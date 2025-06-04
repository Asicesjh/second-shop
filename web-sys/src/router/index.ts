import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/Index.vue'
const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [
            {
                path: '/dashboard',
                component: () => import('@/layout/dashboard.vue'),
                name: 'dashboard',
                meta: {
                    title: '首页',
                    icon: '#icondashboard'
                }
            }
        ]
    },
    {
        path: "/SysUser",
        component: Layout,
        name: "SysUser",
        meta: {
            title: "系统管理",
            icon: "Setting",
            roles: ["sys:manage"],
        },
        children: [
            {
                path: "/adminUser",
                component: () => import('@/view/SysUser/AdminUser.vue'),
                name: "adminUser",
                meta: {
                    title: "管理员管理",
                    icon: "UserFilled",
                    roles: ["sys:adminUser"],
                },
            }
        ],
    },
    {
        path: "/AppUser",
        component: Layout,
        name: "AppUser",
        meta: {
            title: "用户管理",
            icon: "Setting",
            roles: ["sys:manage"],
        },
        children: [
            {
                path: "/wxUser",
                component: () => import('@/view/AppUser/WxUser.vue'),
                name: "wxUser",
                meta: {
                    title: "用户管理",
                    icon: "UserFilled",
                    roles: ["sys:wxUser"],
                },
            }
        ],
    },
    {
        path: "/Cinema",
        component: Layout,
        name: "Cinema",
        meta: {
            title: "电影管理",
            icon: "Setting",
            roles: ["sys:manage"],
        },
        children: [
            {
                path: "/type",
                component: () => import('@/view/Cinema/Type.vue'),
                name: "type",
                meta: {
                    title: "电影分类",
                    icon: "UserFilled",
                    roles: ["sys:type"],
                },
            },
            {
                path: "/cinemaList",
                component: () => import('@/view/Cinema/CinemaList.vue'),
                name: "cinemaList",
                meta: {
                    title: "电影列表",
                    icon: "UserFilled",
                    roles: ["sys:type"],
                },
            }
        ],
    },
    {
        path: "/Recommend",
        component: Layout,
        name: "Recommend",
        meta: {
            title: "首页推荐",
            icon: "Setting",
            roles: ["sys:manage"],
        },
        children: [
            {
                path: "/swiper",
                component: () => import('@/view/Recommend/Swiper.vue'),
                name: "swiper",
                meta: {
                    title: "推荐管理",
                    icon: "UserFilled",
                    roles: ["sys:swiper"],
                },
            }
        ],
    },
    {
        path: "/Order",
        component: Layout,
        name: "Order",
        meta: {
            title: "订单管理",
            icon: "Setting",
            roles: ["sys:manage"],
        },
        children: [
            {
                path: "/ordermanage",
                component: () => import('@/view/Order/OrderManage.vue'),
                name: "ordermanage",
                meta: {
                    title: "订单",
                    icon: "UserFilled",
                    roles: ["sys:ordermanage"],
                },
            }
        ],
    }

]
const router = createRouter({
    history: createWebHistory(),
    routes
})
export default router