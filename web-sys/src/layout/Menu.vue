<template>
    <Logo></Logo>
    <el-menu :default-active="activeIndex" class="el-menu-vertical-demo" :collapse="isCollapse" unique-opened background-color="#304156"
        router>
        <MenuItem :menuList="menuList">
        </MenuItem>
    </el-menu>
</template>
<script setup lang="ts">
import { reactive, computed } from 'vue'
import MenuItem from "@/layout/MenuItem.vue";
import { useRoute } from 'vue-router';
import Logo from './Logo.vue';
import { collapseStore } from '../store/collapse/index';
const collStore = collapseStore()
const isCollapse = computed(()=>{
return collStore.getCollapse
})
const route = useRoute();
//获取激活的菜单
const activeIndex = computed(() => {
    const { path } = route;
    return path;
})
let menuList = reactive([
    {
        path: "/dashboard",
        component: "Layout",
        name: "dashboard",
        meta: {
            title: "首页",
            icon: "HomeFilled",
            roles: ["sys:dashboard"],
        },
    },
    {
        path: "/SysUser",
        component: "Layout",
        name: "SysUser",
        meta: {
            title: "系统管理",
            icon: "Setting",
            roles: ["sys:manage"],
        },
        children: [
            {
                path: "/adminUser",
                component: "/SysUser/AdminUser",
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
        component: "Layout",
        name: "AppUser",
        meta: {
            title: "用户管理",
            icon: "Setting",
            roles: ["sys:manage"],
        },
        children: [
            {
                path: "/wxUser",
                component: "/AppUser/wxUser",
                name: "wxUser",
                meta: {
                    title: "用户列表",
                    icon: "UserFilled",
                    roles: ["sys:wxUser"],
                },
            }
        ],
    },
    {
        path: "/Cineam",
        component: "Layout",
        name: "Cineam",
        meta: {
            title: "电影管理",
            icon: "Setting",
            roles: ["sys:manage"],
        },
        children: [
            {
                path: "/type",
                component: "/Cinema/type",
                name: "type",
                meta: {
                    title: "电影分类",
                    icon: "CirclePlus",
                    roles: ["sys:type"],
                },
            },
            {
                path: "/cinemaList",
                component: "/Cinema/cinemaList",
                name: "cinemaList",
                meta: {
                    title: "电影列表",
                    icon: "List",
                    roles: ["sys:cineamList"],
                },
            }
        ],
    },
    {
        path: "/Recommend",
        component: "Layout",
        name: "Recommend",
        meta: {
            title: "首页推荐",
            icon: "Setting",
            roles: ["sys:manage"],
        },
        children: [
            {
                path: "/swiper",
                component: "/Recommend/swiper",
                name: "swiper",
                meta: {
                    title: "推荐管理",
                    icon: "CirclePlus",
                    roles: ["sys:swiper"],
                },
            }
        ],
    },
    {
        path: "/Order",
        component: "Layout",
        name: "Order",
        meta: {
            title: "订单管理",
            icon: "Setting",
            roles: ["sys:manage"],
        },
        children: [
            {
                path: "/ordermanage",
                component: "/Order/ordermanage",
                name: "ordermanage",
                meta: {
                    title: "订单",
                    icon: "CirclePlus",
                    roles: ["sys:ordermanage"],
                },
            }
        ],
    },
]);
</script>
<style scoped lang="scss">
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 230px;
    min-height: 400px;
    color: white;
}

.el-menu {
    border-right: none;
}

:deep(.el-sub-menu .el-sub-menu__title) {
    color: #f4f4f5 !important;
}

:deep(.el-menu .el-menu-item) {
    color: white;

}

/* 菜单点中文字的颜色 */
:deep(.el-menu-item.is-active) {
    color: white !important;
}

/* 当前打开菜单的所有子菜单颜色 */
:deep(.is-opened .el-menu-item) {
    background-color: #1f2d3d !important;
}

/* 鼠标移动菜单的颜色 */
:deep(.el-menu-item:hover) {
    background-color: #001528 !important;
}
</style>