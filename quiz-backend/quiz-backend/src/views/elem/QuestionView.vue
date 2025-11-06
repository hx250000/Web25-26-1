<template>
  <el-container>
    <!--<el-header>Header</el-header>-->
    <el-header style="font-size: 40px; background-color: rgb(238, 241, 246)">Quiz后台管理</el-header>
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
          <el-input v-model="formInline.user" placeholder="请输入题目关键词"></el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
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
        <el-table-column label="题目" width="200">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.question }}</span>
          </template>
        </el-table-column>

        <el-table-column label="选项" width="300">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.options }}</span>
          </template>
        </el-table-column>

        <el-table-column label="答案" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.answer }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <br/>
      <!--分页-->
      <el-pagination 
        background
        layout="prev, pager, next"
        :total="1000">
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
          <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
        </div>
      </el-dialog>
    </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
    data() {
      return {
        dialogFormVisible: false,
        form: {
          question: '',
          optiona: '',
          optionb: '',
          optionc: '',
          optiond: '',
          answer: ''
        },
        formInline: {
          user: '',
          region: ''
        },
        tableData: [{
          id:"1",
          question:"法国首都是哪个城市？",
          options:"巴黎;伦敦;纽约;东京",
          answer:"巴黎"
          
        }, {
          id:"2",
          question:"法国首都是哪里？",
          options:"巴黎;伦敦;纽约;东京",
          answer:"巴黎"
        },{
          id:"3",
          question:"法国首都是哪里？",
          options:"巴黎;伦敦;纽约;东京",
          answer:"巴黎"
        },{
          id:"4",
          question:"法国首都是哪里？",
          options:"巴黎;伦敦;纽约;东京",
          answer:"巴黎"
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
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      }
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