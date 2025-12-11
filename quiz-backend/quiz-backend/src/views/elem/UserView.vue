<template>
  <el-container>
    <!--<el-header>Header</el-header>-->
    <el-header style="font-size: 40px; background-color: rgb(238, 241, 246)">
      Quiz后台管理
      <el-button style="float: right; margin-top: 10px;" type="danger" @click="logout">退出登录</el-button>
    </el-header>
    <el-container>
      <!--<el-aside width="200px">Aside</el-aside>-->
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-openeds="['1']">
          <el-submenu index="1">
            <template slot="title">
              <h3>管理选项</h3>
            </template>
            <el-menu-item-group>
              <!--<template slot="title">分组一</template>-->
              <el-menu-item index="1-1" class="">
                <router-link to="/user">用户管理</router-link>
              </el-menu-item>
              <el-menu-item index="1-2">
                <router-link to="/question">题目管理</router-link>
              </el-menu-item>
            </el-menu-item-group>

          </el-submenu>

        </el-menu>
      </el-aside>

      <el-main>
        <!--顶部查询表单-->
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item label="用户名">
            <el-input v-model="formInline.keyword" placeholder="请输入用户名"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="onSearch">查询</el-button>
            <el-button type="success" @click="onAddNewUser">添加用户</el-button>

          </el-form-item>
        </el-form>

        <!--中间的table-->
        <el-table :data="tableData" style="width: 80%">
          <el-table-column label="序号" width="80">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column label="添加日期" width="300">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.updateTime }}</span>
            </template>
          </el-table-column>
          <el-table-column label="姓名" width="200">

            <template slot-scope="scope">


              <span style="margin-left: 10px">{{ scope.row.userName }}</span>


            </template>

          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">重置密码</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <br />
        <!--分页-->
        <el-pagination background layout="prev, pager, next" :page-size="pageSize" :total="total"
          :current-page="currentPage" @current-change="handlePageChange">
        </el-pagination>
        <!--弹出添加用户对话框-->
        <el-dialog title="添加新用户" :visible.sync="dialogFormVisible">
          <el-form :model="form">
            <el-form-item label="用户名" :label-width="formLabelWidth">
              <el-input v-model="form.userName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="密  码" :label-width="formLabelWidth">
              <el-input v-model="form.userPassword" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" :label-width="formLabelWidth">
              <el-input v-model="form.checkPassword" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="用户角色" :label-width="formLabelWidth">
              <el-select v-model="form.userRole" placeholder="请选择用户角色">
                <el-option label="普通用户" :value="0"></el-option>
                <el-option label="管理员" :value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="onSubmitNewUser">确 定</el-button>
          </div>
        </el-dialog>
        <!--弹出重置密码对话框-->
        <el-dialog title="重置密码" :visible.sync="dialogResetVisible">
          <el-form :model="formReset">
            <el-form-item label="用户名" :label-width="formLabelWidth">
              <el-input v-model="formReset.userName" disabled></el-input>
            </el-form-item>
            <el-form-item label="新密码" :label-width="formLabelWidth">
              <el-input v-model="formReset.newPassword" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogResetVisible = false">取 消</el-button>
            <el-button type="primary" @click="onResetPassword">确 定</el-button>
          </div>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      dialogFormVisible: false,
      dialogResetVisible: false,
      pageSize: 5, // 每页显示的条数
      total: 0,    // 总条数
      currentPage: 1,  //首页
      form: {
        userName: '',
        userPassword: '',
        checkPassword: '',
        userRole: 0
      },
      formReset: {
        userName: '',
        newPassword: '123456'
      },
      formInline: {
        keyword: ''
      },
      tableData: [{
        updateTime: '2016-05-02',
        userName: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }]
    }
  },
  methods: {
    onAddNewUser() {
      this.dialogFormVisible = true;
    },
    onSubmit() {
      console.log('submit!');
    },
    logout() {
      // 删除 token
      localStorage.removeItem('jwt_token');
      // 跳转到登录页面
      this.$router.push('/login');
      this.$message({
        type: 'success',
        message: '已退出登录',
      });
    },
    handleEdit(index, row) {
      this.formReset.userName = row.userName;   // 自动带入用户名
      this.formReset.newPassword = "123456";    // 默认新密码
      this.dialogResetVisible = true;           // 打开对话框
      console.log(index, row);
    },
    handleDelete(index, row) {
      const id = row.id; // 获取当前用户的 id
      this.$confirm("此操作将永久删除该用户, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 调用删除接口（GET 请求）
          axios
            .delete(`/DeleteById?id=${id}`) // 使用 GET 请求传递 id 参数
            .then((response) => {
              console.log(response.data);
              // 删除成功后刷新当前页数据
              this.handlePageChange(this.currentPage);
            })
            .catch((error) => {
              console.error("Error deleting question:", error);
              this.$message({
                type: "error",
                message: error.response.data.message || "删除用户失败，请稍后重试!",
              });
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    onSubmitNewUser() {
      console.log("Submitting user:", this.form);
      axios
        .post("/Register", this.form)
        .then((response) => {
          console.log("user register successfully:", response.data);
          this.$message({
            type: "success",
            message: "用户添加成功!",
          });
          this.dialogFormVisible = false;
          this.handlePageChange(this.currentPage);
        })
        .catch((error) => {
          this.$message({
            type: "error",
            message: error.response.data.message || "用户添加失败，请稍后重试!",
          });
          console.error("Error adding user:", error);
        });
    },
    onResetPassword() {
      console.log("Resetting password for user:", this.formReset);
      axios.
        put(
          `/ResetPassword`,
          null,  // PUT 不能发 body，所以保持 null
          {
            params: {
              userName: this.formReset.userName,
              newPassword: this.formReset.newPassword
            }
          }
        )
        .then((response) => {
          console.log("Password reset successfully:", response.data);
          this.dialogResetVisible = false;
          this.$message({
            type: "success",
            message: "密码重置成功!",
          });
        })
        .catch((error) => {
          console.error("Error resetting password:", error);
          this.$message({
            type: "error",
            message: error.response.data.message || "密码重置失败，请稍后重试!",
          });
        });
    },
    onSearch() {
      // 这里可以添加查询逻辑
      const keyword = this.formInline.keyword;
      console.log("Searching for:", keyword);
      // 例如，调用API获取数据
      if (!keyword) {
        // 如果关键字为空，加载第一页数据
        this.handlePageChange(1);
        return;
      }
      axios
        .get(`/FindUsers?keyword=${this.formInline.keyword}`)
        .then((response) => {
          this.tableData = response.data.data;
          console.log("Search results:", response.data);
          this.total = response.data.data.total;
        })
        .catch((error) => {
          console.error("Error searching questions:", error);
        });
    },
    handlePageChange(page) {
      this.currentPage = page;
      axios
        .get(`/users?page=${this.currentPage}&pageSize=${this.pageSize}`)
        .then((response) => {
          console.log("Fetched users:", response.data);
          this.tableData = response.data.data.rows;
          this.total = response.data.data.total;
        })
        .catch((error) => {
          this.$message({
            type: "error",
            message: error.response.data.message || "获取用户列表失败，请稍后重试!",
          });
          console.error("Error fetching users:", error);
        });
    },
  },
  mounted() {
    // 初始数据加载
    this.handlePageChange(1);
  }
}
</script>

<style>
/*.el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 160px;
  }*/
.el-menu-item a {
  color: inherit;
  text-decoration: none;
  display: block;
  /* 让点击区域覆盖整个菜单项 */
}

/*.el-menu-item a:hover {
    color: inherit;
    text-decoration: none;
  }*/
</style>