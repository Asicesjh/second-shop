<template>
    <el-container class="layout">
        <el-aside width="auto" class="asside">
            <Menu></Menu>
        </el-aside>
        <el-container>
            <el-header class="header">
                <div class="header-left">
                    <Collapse></Collapse>
                    <BreadCrumb></BreadCrumb>
                </div>
            </el-header>
            <el-main class="main"><RouterView></RouterView></el-main>
        </el-container>
    </el-container>
</template>
<script setup lang="ts">
import Menu from './Menu.vue';
import Collapse from './Collapse.vue';
import BreadCrumb from './BreadCrumb.vue';
</script>
<style lang="scss">
.layout {
    height: 100%;

    .asside {
        background-color: #304156;
    }

    .header {
        background-color: gray;
        color: white;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .header-left {
            display: flex;
            align-items: center;
        }
    }

    .main {
        background-color: lightblue;
    }
}
</style>