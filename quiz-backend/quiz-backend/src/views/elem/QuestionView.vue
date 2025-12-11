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
              <el-menu-item index="1-1">
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
          <el-form-item label="题目">
            <el-input v-model="formInline.keyword" placeholder="请输入题目关键词"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="onSearch">查询</el-button>
            <el-button type="success" @click="onAddNewQuestion">添加题目</el-button>

          </el-form-item>
        </el-form>
        <!--中间的table-->
        <el-table :data="tableData" style="width: 80%">
          <el-table-column label="序号" width="50">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column label="题目" width="275">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.questionText }}</span>
            </template>
          </el-table-column>

          <el-table-column label="选项" width="350">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.options }}</span>
            </template>
          </el-table-column>

          <el-table-column label="答案" width="125">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.answer }}</span>
            </template>
          </el-table-column>

          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <br />
        <!--分页-->
        <el-pagination background layout="prev, pager, next" :page-size="pageSize" :total="total"
          :current_page="currentPage" @current-change="handlePageChange">
        </el-pagination>
        <!--弹出添加用户对话框-->
        <el-dialog title="添加新题目" :visible.sync="dialogFormVisible">
          <el-form :model="form">
            <el-form-item label="题  目" :label-width="formLabelWidth">
              <el-input v-model="form.question" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="选项A" :label-width="formLabelWidth">
              <el-input v-model="form.optiona" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="选项B" :label-width="formLabelWidth">
              <el-input v-model="form.optionb" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="选项C" :label-width="formLabelWidth">
              <el-input v-model="form.optionc" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="选项D" :label-width="formLabelWidth">
              <el-input v-model="form.optiond" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="正确答案" :label-width="formLabelWidth">
              <el-input v-model="form.answer" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="onSubmitNewQuestion">确 定</el-button>
          </div>
        </el-dialog>
        <!--弹出修改题目对话框-->
        <el-dialog title="编辑题目" :visible.sync="isEdit">
          <el-form :model="form">
            <el-form-item label="题  目" :label-width="formLabelWidth">
              <el-input v-model="formUpdate.question" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="选项A" :label-width="formLabelWidth">
              <el-input v-model="formUpdate.optiona" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="选项B" :label-width="formLabelWidth">
              <el-input v-model="formUpdate.optionb" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="选项C" :label-width="formLabelWidth">
              <el-input v-model="formUpdate.optionc" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="选项D" :label-width="formLabelWidth">
              <el-input v-model="formUpdate.optiond" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="正确答案" :label-width="formLabelWidth">
              <el-input v-model="formUpdate.answer" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="isEdit = false">取 消</el-button>
            <el-button type="primary" @click="onUpdateQuestion">确 定</el-button>
          </div>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import axios from 'axios';
//import { is } from 'core-js/core/object';
export default {
  data() {
    return {
      dialogFormVisible: false,
      dialogResetVisible: false,
      isEdit: false,
      currentPage: 1,
      pageSize: 5, // 每页显示的条数
      total: 0,    // 总条数

      form: {
        question: '',
        optiona: '',
        optionb: '',
        optionc: '',
        optiond: '',
        answer: ''
      },
      formUpdate: {
        id: '',
        question: '',
        optiona: '',
        optionb: '',
        optionc: '',
        optiond: '',
        answer: ''
      },
      formInline: {
        keyword: ''
      },
      tableData: [{
        id: "1",
        question: "法国首都是哪个城市？",
        options: "巴黎;伦敦;纽约;东京",
        answer: "巴黎"

      }, {
        id: "2",
        question: "法国首都是哪里？",
        options: "巴黎;伦敦;纽约;东京",
        answer: "巴黎"
      }, {
        id: "3",
        question: "法国首都是哪里？",
        options: "巴黎;伦敦;纽约;东京",
        answer: "巴黎"
      }]
    }
  },
  methods: {
    onAddNewQuestion() {
      this.dialogFormVisible = true;
    },

    onSubmit() {
      console.log('submit!');
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
        .get(`/findquestion?keyword=${this.formInline.keyword}`)
        .then((response) => {
          this.tableData = response.data.data;
          console.log("Search results:", response.data);
          // this.total = response.data.data.total;
        })
        .catch((error) => {
          console.error("Error searching questions:", error);
        });
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
      this.isEdit = true;
      this.formUpdate = {
        id: row.id,
        question: row.questionText,
        optiona: row.options[0],
        optionb: row.options[1],
        optionc: row.options[2],
        optiond: row.options[3],
        answer: row.answer
      };
    },
    handleDelete(index, row) {
      console.log(index, row);
      const id = row.id; // 获取当前条目的 id
      this.$confirm("此操作将永久删除该题目, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 调用删除接口（GET 请求）
          axios
            .delete(`/deletequestion?id=${id}`) // 使用 GET 请求传递 id 参数
            .then((response) => {
              console.log(response.data);
              // 删除成功后刷新当前页数据
              this.handlePageChange(this.currentPage);
            })
            .catch((error) => {
              console.error("Error deleting question:", error);
              this.$message({
                type: "error",
                message: error.response.data.message || "删除题目失败，请稍后重试!",
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
    handlePageChange(page) {
      this.currentPage = page;
      axios
        .get(`/questions?page=${this.currentPage}&pageSize=${this.pageSize}`)
        .then((response) => {
          this.tableData = response.data.data.qsBeanList;
          this.total = response.data.data.total;
        })
        .catch((error) => {
          console.error("Error fetching questions:", error);
        });
    },
    onSubmitNewQuestion() {

      console.log("Submitting question:", this.form);
      axios
        .post("/addquestion", this.form)
        .then((response) => {
          this.$message({
            type: "success",
            message: "题目添加成功!",
          });
          console.log("Question added successfully:", response.data);
          this.dialogFormVisible = false;
          this.handlePageChange(this.currentPage);
        })
        .catch((error) => {
          console.error("Error adding question:", error);
          this.$message({
            type: "error",
            message: error.response.data.message || "题目添加失败，请稍后重试!",
          });
        });

    },
    onUpdateQuestion() {
      console.log("Updating question:", this.formUpdate);
      axios
        .post("/updatequestion", this.formUpdate)
        .then((response) => {
          console.log("Question updated successfully:", response.data);
          this.$message({
            type: "success",
            message: "题目更新成功!",
          });
          this.dialogFormVisible = false;
          this.isEdit = false;
          this.handlePageChange(this.currentPage);
        })
        .catch((error) => {
          this.$message({
            type: "error",
            message: error.response.data.message || "题目更新失败，请稍后重试!",
          });
          console.error("Error updating question:", error);
        });
    },
  },


  mounted() {
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
</style>