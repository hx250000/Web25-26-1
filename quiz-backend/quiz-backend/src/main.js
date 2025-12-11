import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import router from './router'
import axios from 'axios';

Vue.use(ElementUI);
Vue.config.productionTip = false

axios.defaults.baseURL = 'http://localhost';
axios.defaults.withCredentials = true;

// 添加请求拦截器
axios.interceptors.request.use(
  (config) => {
    // 从 localStorage 中获取 token
    const token = localStorage.getItem('jwt_token');
    if (token) {
      // 在请求头中添加 token 字段
      config.headers.token = token;
    }
    return config;
  },
  (error) => {
    // 请求错误处理
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  (response) => {
    // 直接返回响应数据
    return response;
  },
  (error) => {
    // 响应错误处理
    if (error.response) {
      const {data, status} = error.response;
      if (status === 401|| data.message === 'NOT_LOGIN') {
        // 处理未授权错误，例如跳转到登录页面
        localStorage.removeItem('jwt_token');
        router.push('/login');
      }else {
        console.erroer('响应错误:', error.message);
      }
    }
    else{
      console.error('响应错误:', error.message);
    }  
  }
);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
