<template>
  <el-main>
    <!-- 搜索栏 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item>
        <el-input placeholder="请输入电影名称" v-model="searchParm.cinemaName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="searchBtn" icon="Search">搜索</el-button>
        <el-button @click="resetBtn" type="danger" plain icon="Close">重置</el-button>
        <el-button icon="Plus" type="primary" @click="addBtn">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :height="tableHeight" :data="tableList" border stripe>
      <el-table-column prop="image" label="电影图片">
        <template #default="scope" style="text-align: center;">
          <el-image v-if="scope.row.image" style="width: 150px; height: auto" :src="scope.row.image.split(',')[0]" />
        </template>
      </el-table-column>
      <el-table-column prop="cinemaName" label="电影名称"></el-table-column>
      <el-table-column prop="category" label="类型"></el-table-column>
      <el-table-column prop="price" label="价格"></el-table-column>
      <el-table-column prop="rating" label="时间"></el-table-column>
      <el-table-column prop="status" label="上架状态">
        <template #default="scope">
          <!-- <el-tag type="success" v-if="scope.row.status == '0'" size="default" effect="dark">上架</el-tag>
          <el-tag type="success" v-if="scope.row.status == '1'" size="default" effect="dark">下架</el-tag> -->
          <el-switch v-model="scope.row.status" :active-value="'0'" :inactive-value="'1'" :before-change="beforeStatus"
            @change="changeStatus(scope.row.status, scope.row.cinemaId)" />
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="danger" icon="Delete" size="default" @click="deleteBtn(scope.row)">删除</el-button>
          <el-button @click="editBtn(scope.row)" icon="Edit" type="primary" size="default">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination @size-change="sizeChange" @current-change="currentChange" :current-page.sync="searchParm.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]" :page-size="searchParm.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="searchParm.total" background>
    </el-pagination>
    <!-- 弹框组件 -->
    <SysDialog :title="dialog.title" :visible="dialog.visible" :width="dialog.width" :height="dialog.height"
      @onClose="onClose" @onConfirm="commit">
      <template v-slot:content style="text-align: center;">
        <el-form :model="addModel" ref="addForm" :rules="rules" label-width="80px" size="default">
          <el-form-item prop="cinemaName" label="名称">
            <el-input v-model="addModel.cinemaName"></el-input>
          </el-form-item>
          <el-form-item prop="price" label="价格">
            <el-input type="number" v-model="addModel.price"></el-input>
          </el-form-item>
          <el-form-item prop="rating" label="时长">
            <el-input  v-model="addModel.rating"></el-input>
          </el-form-item>
          <el-form-item prop="description" label="描述">
            <el-input  v-model="addModel.description"></el-input>
          </el-form-item>
          <el-form-item prop="category" label="分类">
            <el-select v-model="addModel.category" class="m-2" placeholder="请选择分类" size="default" style="width: 100%">
              <el-option v-for="item in selectList" :key="item['value']" :label="item['label']"
                :value="item['value']" />
            </el-select>
          </el-form-item>
          <el-form-item prop="images" label="图片">
            <UploadImage ref="upImgRef" @getImg="getImg" :oldUrl="oldUrl" :fileList="fileList">
            </UploadImage>
          </el-form-item>
          <el-form-item prop="status" label="状态:">
            <el-radio-group v-model="addModel.status">
              <el-radio :label="'0'">上架</el-radio>
              <el-radio :label="'1'">下架</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </template>
    </SysDialog>
  </el-main>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref, nextTick } from "vue";
import { getCinemaListApi, addCinemaApi, deleteApi, editApi, upandownApi } from "../../api/cinemalist/index";
import useDialog from "../../hooks/UseDialog";
import { EditType } from "../../type/BaseEnum";
import useInstance from "../../hooks/useInstance";
import { ElMessage, type FormInstance, type UploadUserFile } from "element-plus";
import SysDialog from "@/components/SysDialog.vue";
import { type NewType } from "../../type/BaseType.ts";
import UploadImage from "@/components/UploadImage.vue";
import type { CinemaList } from "../../api/cinemalist/CinemaListModel.ts";
import { listApi } from "../../api/cinemacategory/index.ts";
//获取全局属性
const { global } = useInstance()
//表单ref属性
const addForm = ref<FormInstance>();
//图片上传的ref属性
const upImgRef = ref();
//图片初始值
const fileList = ref<Array<UploadUserFile>>([]);
//回显的图片
const oldUrl = ref<Array<{ url: string }>>([]);
//图片地址
const imgUrl = ref<Array<{ url: string }>>([]);
//弹框属性
const { dialog, onClose, onShow } = useDialog();
//搜索栏绑定对象
const searchParm = reactive({
  currentPage: 1,
  pageSize: 10,
  cinemaName: "",
  total: 0,
});
const selectParm = reactive({
  currentPage: 1,
  pageSize: 10,
  categoryName: "",
  total: 0,
});
//表单绑定对象
const addModel = reactive({
  cinemaId: "",
  cinemaName: "",
  price: "",
  category: "",
  image: "",
  status: "",
  rating: "",
  description:""
});
//表单验证规则
const rules = reactive({
  cinemaName: [
    { required: true, message: "请填写名称", trigger: "change" },
  ],
  price: [
    { required: true, message: "请填写价格", trigger: "change" },
  ],
  category: [
    { required: true, message: "请填写分类", trigger: "change" },
  ],
  image: [
    { required: true, trigger: "change", message: "请上传图片" },
  ],
  status: [
    { required: true, trigger: "change", message: "请选择状态" },
  ],
  rating: [
    { required: true, trigger: "change", message: "电影片长" },
  ],
  description:[
    { required: true, trigger: "change", message: "电影描述" },
  ]
});
//接收图片上传地址
const getImg = (img: NewType) => {
  console.log("999");
  console.log(img);
  imgUrl.value = oldUrl.value.concat(img.newImgUrl);
  if (img.deleteUrl.length > 0) {
    let newArr = imgUrl.value.filter(
      (x) => !img.deleteUrl.some((item: { url: string; }) => x.url === item.url)
    );
    imgUrl.value = newArr;
  }
  // console.log(imgUrl.value)
  //把图片路径拼接为逗号分隔的字符串
  let url = "";
  for (let k = 0; k < imgUrl.value.length; k++) {
    url = url + imgUrl.value[k].url + ",";
  }
  addModel.image = url.substring(0, url.lastIndexOf(","));
  // console.log(addModel.image);
};
//状态：0 新增 1编辑
const tags = ref('')
//表格数据
const tableList = ref([]);
//定义商品数据
const selectList = ref([]);
//获取表格数据
const getSelect = async () => {
  let res = await listApi(selectParm)
  if (res && res.code == 200) {
    const arr = res.data.records.map((item: { categoryName: any; }) => item.categoryName);
    // selectList.value = res.data.records
    const options = arr.map((tag: any) => ({
      value: tag,
      label: tag,
    }));
    // console.log(options)
    selectList.value = options
  }
  // console.log(res.data.records)
  // console.log(selectList.value)

}
const getCinemaList = async () => {
  let res = await getCinemaListApi(searchParm);
  if (res && res.code == 200) {
    // console.log(res.data.records);
    tableList.value = res.data.records;
    searchParm.total = res.data.total;
  }
};
//上架下架操作之前的事件
const beforeStatus = async () => {
  let confirm = await global.$myconfirm("确定上下架吗？");
  return new Promise((resolve, reject) => {
    if (confirm) {
      return resolve(confirm);
    } else {
      return reject(confirm);
    }
  });
};
//上下架操作
const changeStatus = async (type: string, cinemaId: string) => {
  console.log(type);
  console.log(cinemaId);
  let res = await upandownApi({
    cinemaId: cinemaId,
    status: type,
    cinemaName: "",
    image: "",
    price: "",
    category: ""
  });
  if (res && res.code == 200) {
    getCinemaList();
  }
};
//新增按钮
const addBtn = () => {
  tags.value = '0'
  imgUrl.value = [];
  oldUrl.value = [];
  fileList.value = [];
  dialog.height = 500;
  //弹框显示
  onShow();
  //清空表单
  addForm.value?.resetFields()
};
//删除
const deleteBtn = async (row: CinemaList) => {
  console.log(row);
  let confirm = await global.$myconfirm("确实删除该数据吗?");
  if (confirm) {
    let res = await deleteApi(row);
    if (res && res.code == 200) {
      ElMessage.success(res.msg);
      getCinemaList()
    }
  }
};
//编辑
const editBtn = (row: CinemaList) => {
  tags.value = "1";
  //清空图片数据
  imgUrl.value = [];
  oldUrl.value = [];
  fileList.value = [];
  dialog.width = 700;
  dialog.height = 400;
  // 获取商品数据
  getCinemaList();
  console.log(row);
  onShow();
  // 数据回显
  nextTick(() => {
    Object.assign(addModel, row);
    //图片回显
    if (addModel.image) {
      //逗号分隔转数组
      let imgs = addModel.image.split(",");
      for (let i = 0; i < imgs.length; i++) {
        let img = { name: "", url: "" };
        img.name = imgs[i];
        img.url = imgs[i];
        fileList.value.push(img);
        oldUrl.value.push({ url: imgs[i] });
      }
    }
  });
  //清空表单
  addForm.value?.resetFields();
};
//表单提交
const commit = () => {
  //表单验证
  addForm.value?.validate(async (valid) => {
    if (valid) {
      console.log('验证通过')
      //提交
      let res = null;
      if (tags.value == EditType.ADD) {
        res = await addCinemaApi(addModel)
      } else {
        res = await editApi(addModel)
      }
      if (res && res.code == 200) {
        ElMessage.success(res.msg)
        //刷新表格数据
        getCinemaList()
        onClose()
      }
    }
  })
}
//页容量改变时触发
const sizeChange = (size: number) => {
  searchParm.pageSize = size;
  getCinemaList();
};
//页数改变时触发
const currentChange = (page: number) => {
  searchParm.currentPage = page;
  getCinemaList();
};
//重置
const resetBtn = () => {
  searchParm.cinemaName = '';
  getCinemaList();
}
//搜索
const searchBtn = () => {
  getCinemaList();
}
//表格高度
const tableHeight = ref(0);
onMounted(() => {
  getCinemaList();
  getSelect();
  nextTick(() => {
    //计算表格高度
    tableHeight.value = window.innerHeight - 200;
  });
});
</script>
<style scoped>
.el-table .cell {
  text-align: center;
}
</style>