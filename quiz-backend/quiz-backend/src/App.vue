<template>
  <div id="app">
    <!--<h1 id="htitle"> {{message}} </h1>
    <test-button></test-button>-->
    <!--<user-view></user-view>-->
    <!--<question-view></question-view>-->
    <router-view></router-view>
  </div>
</template>

<script>
//import TestButton from  './views/elem/testButton.vue';
//import UserView from  './views/elem/UserView.vue';
//import QuestionView from  './views/elem/QuestionView.vue';
    export default{
        components:{},
        data(){
          return  {
            message:"hello vue",
              //  num:1,
          };
        },
        methods:{
          handleChange(value) {
            console.log(value);
          }
        },
    };
</script>

<style>
#inp{
    margin-top:20px;
}
#htitle{
    color:red;
}
</style>