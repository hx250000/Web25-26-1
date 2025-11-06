<template>
    <div>
    
        <el-row>
            <el-button round>圆角按钮</el-button>
            <el-button type="primary" round>主要按钮</el-button>
            <el-button type="success" round>成功按钮</el-button>
            <el-button type="info" round>信息按钮</el-button>
            <el-button type="warning" round>警告按钮</el-button>
            <el-button type="danger" round>危险按钮</el-button>
        </el-row>
        <el-input-number 
            id="inp" 
            v-model="num" 
            @change="handleChange" 
            :min="1" 
            :max="10" 
            label="描述文字">

        </el-input-number>

    </div>
</template>

<script>
export default {
    data(){
            return{
                //message:"hello vue",
                num:1,
            };
        },
        methods:{
          handleChange(value) {
            console.log(value);
          }
        },
}
</script>

<style>

</style>
