<template>
    <el-main>
        <!-- 搜索栏 -->
        <el-form :model="searchParm" :inline="true" size="default">
            <el-form-item>
                <el-input v-model="searchParm.nickName" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button icon="Search" @click="searchBtn">搜索</el-button>
                <el-button icon="Close" type="danger" plain @click="resetBtn">重置</el-button>
                    <el-button type="primary" icon="Plus" @click="addBtn">新增</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :height="tableHeight" :data="tableList" border stripe>
            <el-table-column prop="nickName" label="姓名"></el-table-column>
            <el-table-column prop="sex" label="性别">
                <template #default="scope">
                    <el-tag v-if="scope.row.sex == '1'" type="danger" effect="dark">女
                    </el-tag>
                    <el-tag v-if="scope.row.sex == '0'" effect="dark">男</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column prop="username" label="账户"></el-table-column>
            <el-table-column prop="status" label="状态">
                <template #default="scope">
                    <el-tag v-if="scope.row.status == '1'" type="danger" effect="dark">停用</el-tag>
                    <el-tag v-if="scope.row.status == '0'" effect="dark">启用</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="220" align="center">
                <template #default="scope">
                    <el-button type="primary" icon="Edit" size="default" @click="editBtn(scope.row)">编辑</el-button>
                    <el-button type="danger" icon="Delete" size="default" @click="deleteBtn(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination @size-change="sizeChange" @current-change="currentChange"
            :current-page.sync="searchParm.currentPage" :page-sizes="[10, 20, 40, 80, 100]"
            :page-size="searchParm.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="searchParm.total"
            background>
        </el-pagination>
        <!-- 新增、编辑弹框 -->
        <SysDialog :title="dialog.title" :width="dialog.width" :height="dialog.height" :visible="dialog.visible"
            @on-close="onClose" @on-confirm="commit">
            <template v-slot:content>
                <el-form :model="addModel" ref="addRef" :rules="rules" label-width="80px" :inline="false"
                    size="default">
                    <el-form-item prop="nickName" label="姓名:">
                        <el-input v-model="addModel.nickName"></el-input>
                    </el-form-item>
                    <el-form-item prop="sex" label="性别:">
                        <el-radio-group v-model="addModel.sex">
                            <el-radio :label="'0'">男</el-radio>
                            <el-radio :label="'1'">女</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item prop="phone" label="电话:">
                        <el-input v-model="addModel.phone"></el-input>
                    </el-form-item>
                    <el-form-item prop="username" label="账号:">
                        <el-input v-model="addModel.username"></el-input>
                    </el-form-item>
                    <el-form-item prop="password" label="密码:">
                        <el-input v-model="addModel.password"></el-input>
                    </el-form-item>
                    <el-form-item prop="status" label="状态:">
                        <el-radio-group v-model="addModel.status">
                            <el-radio :label="'0'">启用</el-radio>
                            <el-radio :label="'1'">停用</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-form>
            </template>
        </SysDialog>
    </el-main>
</template>
<script setup lang="ts">
import { nextTick, onMounted, reactive, ref } from "vue";
import SysDialog from "@/components/SysDialog.vue";
import useDialog from "../../hooks/UseDialog";
import { ElMessage, type FormInstance } from "element-plus";
import { addUserApi, getListApi, editApi, deleteApi } from "../../api/sysuser/index";
import { type SysUser } from '../../api/sysuser/SysUserModel'
import { Title } from '../../type/BaseEnum'
import useInstance from '../../hooks/useInstance'
//获取全局属性
const { global } = useInstance()
//表单ref属性
const addRef = ref<FormInstance>();
//获取弹框属性
const { dialog, onClose } = useDialog();
//搜索绑定的对象
const searchParm = reactive({
    nickName: "",
    pageSize: 5,
    currentPage: 1,
    total: 0
});
//表格高度
const tableHeight = ref(0)
//表格数据
const tableList = ref([])
const tags = ref('')
//新增按钮
const addBtn = () => {
    tags.value = '0'
    //设置弹框标题
    dialog.title = Title.EDIT
    dialog.height = 300;
    dialog.visible = true;
    //清空表单
    addRef.value?.resetFields()
};
//新增表单绑定的对象
const addModel = reactive({
    userId: "",
    username: "",
    password: "",
    nickName: "",
    phone: "",
    sex: "",
    status: "",
});
//表单验证规则
const rules = {
    nickName: [{ required: true, message: "请输入姓名", trigger: "blur" }],
    sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
    phone: [{ required: true, message: "请输入电话", trigger: "blur" }],
    username: [{ required: true, message: "请输入账户", trigger: "blur" }],
    password: [{ required: true, message: "请输入密码", trigger: "blur" }],
    status: [{ required: true, message: "请选择状态", trigger: "blur" }],
};
//列表查询
const getList = async () => {
    let res = await getListApi(searchParm)
    if (res && res.code == 200) {
        console.log(res)
        tableList.value = res.data.records;
        searchParm.total = res.data.total;
    }
}
//编辑
const editBtn = (row: SysUser) => {
    tags.value = '1'
    console.log(row)
    //设置弹框标题
    dialog.title = Title.EDIT
    dialog.height = 300;
    dialog.visible = true;
    //设置数据回显
    nextTick(() => {
        Object.assign(addModel, row)
    })
}
//新增、编辑提交
const commit = () => {
    console.log(tags.value)
    //表单验证
    addRef.value?.validate(async (valid) => {
        if (valid) {
            console.log(addModel);
            //提交数据
            let res = null;
            if (tags.value == '0') {
                res = await addUserApi(addModel);
            } else {
                res = await editApi(addModel);
            }
            if (res && res.code == 200) {
                //信息提示
                ElMessage.success(res.msg)
                //刷新表格
                getList()
                //关闭弹框
                dialog.visible = false;
            }
        }
    });
};
//删除
const deleteBtn = async (row: SysUser) => {
    console.log(row)
    //信息提示
    console.log(global)
    const confirm = await global.$myconfirm('确定删除该数据吗?')
    if (confirm) {
        let res = await deleteApi(row.userId as string)
        if (res && res.code == 200) {
            ElMessage.success(res.msg)
            getList()
        }
    }
}
//搜索
const searchBtn = () => {
    getList()
}
//重置
const resetBtn = () => {
    searchParm.nickName = ''
    getList()
}
//页容量改变时触发
const sizeChange = (size: number) => {
    console.log(size)
}
//页数改变时触发
const currentChange = (page: number) => {
    console.log(page)
}
onMounted(() => {
    //计算表格高度
    tableHeight.value = window.innerHeight - 200
    getList()
})
</script>
<style scoped></style>