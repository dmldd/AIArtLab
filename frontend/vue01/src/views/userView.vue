<template>
  <div>
    <div style="margin: 10px 0px;">
      <el-input style="width: 300px;margin-right: 5px;" placeholder="请输入名字" v-model="userName"></el-input>
      <el-button type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <!-- 新增 -->
    <div style="margin: 10px 0px;">
      <el-button type="primary" @click="add_button">新增</el-button>
      <!-- 弹窗 -->
      <el-dialog title="添加用户" :visible.sync="dialogFormVisible_add" @close="changeFlag">
        <el-form :model="form">
          <el-form-item label="用户名" :label-width="formLabelWidth">
            <el-input v-model="form.userName" autocomplete="off" :disabled="edit_flag"></el-input>
          </el-form-item>
          <el-form-item label="新用户名" :label-width="formLabelWidth" v-if="edit_flag">
            <el-input  v-model="form.NewUserName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="昵称"     :label-width="formLabelWidth">
            <el-input   v-model="form.nickname"   autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="生成次数" :label-width="formLabelWidth">
            <el-input v-model="form.num" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible_add = false; add_flag = false; edit_flag = false">取 消</el-button>
          <el-button v-if="add_flag" type="primary" @click='addHandle'>添加提交</el-button>
          <el-button v-if="edit_flag" type="primary" @click='editHandle'>修改提交</el-button>
        </div>
      </el-dialog>

      <el-popconfirm title="您确定批量删除吗？" @confirm="deleteBatchButton" confirm-button-text='好的' cancel-button-text='不用了'
        style="margin: 0px 10px;">
        <el-button type="danger" slot="reference">批量删除</el-button>
      </el-popconfirm>


    </div>
    <!-- 表格 -->
    <el-table :data="tableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="id" label="用户id" width="100px">
      </el-table-column>
      <el-table-column prop="userName" label="用户名" width="120">
      </el-table-column>
      <el-table-column prop="nickname" label="昵称" width="140">
      </el-table-column>
      <el-table-column prop="num" label="生成次数" width="140">
      </el-table-column>
      <!-- <el-table-column prop="email" label="邮箱" width="140">
            </el-table-column> -->

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button style="margin-right: 5px;" type="warning" @click="edit_button(scope.row)">编辑 <i
              class="el-icon-edit"></i> </el-button>
          <el-popconfirm confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info" icon-color="red"
            title="您确定删除吗？" @confirm="delete_button(scope.row)">
            <el-button slot="reference" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="total"
      :page-size="pageSize">
    </el-pagination>

    <!-- 修改sd的网址 -->
    <div style="margin-top: 50px;margin-bottom: 10px;"><span >修改通道网址</span></div>
    <el-input v-model="id"></el-input>
    <el-input v-model="url"></el-input>
    <el-button @click="updateUrl">提交</el-button>
    <br>
    1："麦橘,dreamshape,breakdomain"；2："通道2"  3："SHMILY,ReVAnimated,Manmarumix" 4："通道2" 

    <div style="margin-top: 30px;">添加注册码</div>
    <el-input v-model="recommendCode"> </el-input>
    <el-button @click="submitRecommendCode">提交</el-button>







    <!-- ---------- -->
    <el-menu
      :default-active="active_menue"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose">
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>导航一</span>
        </template>
        <el-menu-item-group>
          <template slot="title">分组一</template>
          <el-menu-item index="1-1">选项1</el-menu-item>
          <el-menu-item index="1-2">选项2</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="分组2">
          <el-menu-item index="1-3">选项3</el-menu-item>
        </el-menu-item-group>
        <el-submenu index="1-4">
          <template slot="title">选项4</template>
          <el-menu-item index="1-4-1">选项1</el-menu-item>
        </el-submenu>
      </el-submenu>
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <span slot="title">导航二</span>
      </el-menu-item>
      <el-menu-item index="3" disabled>
        <i class="el-icon-document"></i>
        <span slot="title">导航三</span>
      </el-menu-item>
      <el-menu-item index="4">
        <i class="el-icon-setting"></i>
        <span slot="title">导航四</span>
      </el-menu-item>
    </el-menu>


    <el-button @click="toTag('1')">to 1</el-button>
    <el-button @click="toTag('2')">to 2</el-button>
    <el-button @click="toTag('3')">to 3</el-button>
    









</div>
</template>
  
<script>
import request from '@/utils/request';
export default {

  data: function () {
    return {
      active_menue:"1",



      portraits: [
        { id: 1, name: 'Portrait 1', image: 'path/to/image1.jpg' },
        { id: 2, name: 'Portrait 2', image: 'path/to/image2.jpg' },
        { id: 3, name: 'Portrait 3', image: 'path/to/image3.jpg' },
        { id: 4, name: 'Portrait 4', image: 'path/to/image2.jpg' },
        { id: 5, name: 'Portrait 5', image: 'path/to/image2.jpg' },
        { id: 6, name: 'Portrait 6', image: 'path/to/image2.jpg' },

        // ...更多肖像数据
      ],
      selectedPortrait: null,
      hoverPortrait: null,
      recommendCode:null,
      id:null,
      url:null,
      tableData: [],
      total: 100,
      pageSize: 5,
      pageNum: 1,
      userName: '',
      form: {
        userName: '',
        NewUserName: '',
        nickname: '',
        email: '',
        address: '',
        id: '',
        num: '',
      },
      dialogFormVisible_add: false,
      edit_flag: false,
      add_flag: false,
      formLabelWidth: '120px',
      multipleSelection: [],
    }
  },
  methods: {
    handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },

      toTag(num){
        this.active_menue=num
      },


    selectPortrait(id) {
      this.selectedPortrait = id;
    },
   
    addToInput(color) {
      if (!this.input.includes(color)) {
        this.input += (this.input.length > 0 ? ', ' : '') + color;
      }
    },
    removeColor(color) {
      let colorsArray = this.input.split(', ');
      const index = colorsArray.indexOf(color);
      if (index !== -1) {
        colorsArray.splice(index, 1);
        this.input = colorsArray.join(', ');
      }
    },
    submitRecommendCode(){
      request.get("/admin/addRecommendCode",{
        params:{
          recommendCode:this.recommendCode
        }
      }).then(res=>{
        if(res!=null){
          if(res.code==0){
            this.$message.success("添加成功")
          }
          else this.$message.error(res.message)
        }
      })
    },
    load: function () {
      request.get("/user/findPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userName: this.userName,
        }
      }).then(res => {
        if (res != null) {
          this.total = res.data.total; this.tableData = res.data.data
        }
      })
    },

// 修改sd网址
    updateUrl(){
      request.get("/user/updateUrl",{
        params:{
          id:this.id,
          url:this.url
        }
      }).then(res=>{
        if(res!=null){
          this.$message.success("修改成功")
        }
      })
    },


    //改变页码后请求数据  
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },
    reset() {
      this.userName = '';
      this.load();
    },
    //添加成员
    addHandle() {
      request.post("/user/add", this.form).then(res => {
        if (res != null) {
          if (res.code == 0) {
            this.$message.success("添加成功")
            this.dialogFormVisible_add = false
            this.add_flag = false
            this.load();
          }
          else this.$message.error(res.message)
        }
        else {
          this.$message.error("添加失败")
        }
      })
    },
    editHandle() {
      let ifUserNameChange = 0   //0表示username没改变 1变了
      if (this.form.NewUserName != this.form.userName) {
        ifUserNameChange = 1
        this.form.userName = this.form.NewUserName
      }
      request.post("/user/edit", this.form, {
        params: {
          flag: ifUserNameChange
        }
      }).then(res => {
        if (res) {
          this.$message.success("编辑成功")
          this.dialogFormVisible_add = false
          this.edit_flag = false
          this.load();
        }
        else {
          this.$message.error("编辑失败")
        }
      })
    },
    add_button() {
      this.dialogFormVisible_add = true;
      this.form = {};
      this.add_flag = true;
    },
    edit_button(row) {
      this.form = row;
      this.form.NewUserName=this.form.userName
      this.dialogFormVisible_add = true;
      this.edit_flag = true;

    },
    changeFlag() {
      this.add_flag = false;
      this.edit_flag = false
    },
    delete_button(row) {
      this.form = row;
      request.post("/user/delete", this.form).then(res => {
        if (res) {
          this.$message.success("删除成功")
          this.load();
        }
        else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      let ids = val.map(v => v.id)
      this.multipleSelection = ids
    },
    //批量删除按钮
    deleteBatchButton() {
      if (this.multipleSelection == "") {
        alert("亲，请勾选数据后再点击哦~")
      }
      else {
        request.post("/user/deleteBatch", JSON.stringify(this.multipleSelection)).then(res => {
          if (res) {
            this.$message.success("批量删除成功")
            this.load()
          }
          else {
            this.$message.error("批量删除失败")
          }
        })
      }
    },

    
  },

  created() {
    this.load();    
  },

  mounted() {

  }

  }
</script>


<style>
.gallery {
  height: 400px;
  overflow-y: auto;
}

.portrait-card {
  cursor: pointer;
  position: relative;
  border: 1px solid transparent;
  transition: border-color 0.3s;
  padding-top: 100%; /* 1:1 Aspect Ratio */
}

.portrait-card:hover,
.portrait-card.is-selected {
  border-color: #409eff;
}

.image-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%; /* Adjust this to change the image size */
  height: 0;
  padding-bottom: 100%; /* 1:1 Aspect Ratio */
  overflow: hidden;
  border-radius: 10px;
}

.image-container img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  text-align: center;
  padding: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}






.container {
  max-width: 1213px;
  margin: 0 auto;
  overflow-x: hidden;
}

.el-col {
  padding-right: 2px;
  padding-left: 2px;
}
.el-scrollbar__bar.is-horizontal {
  display: none; /* 隐藏横向滚动条 */
}
.scroll-container {
  height: 400px;
  overflow-y: auto;
  overflow-x: hidden;
}
.selected-colors-container {
  height: 100px; /* 根据需要调整高度 */
  overflow-y: auto;
  overflow-x: hidden;
}
</style>
  