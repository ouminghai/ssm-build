import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
import env from "../../build/env";

Vue.use(VueAxios,axios);

//定义axios的拦截器
Vue.axios.interceptors.request.use(function(config) {
  //在发送请求之前做某事
    const ajaxUrl = env === 'development'
        ? 'http://127.0.0.1:8080'
        : env === 'production'
            ? 'http://127.0.0.1:8080'
            : 'http://127.0.0.1:8080';
    config.baseURL = ajaxUrl;
    //config.headers['Content-Type'] = 'multipart/form-data';

    //config.headers['Content-Type'] = 'application/x-www-form-urlencoded';
    return config;

},function(error) {
    // Do something with response error
    return Promise.reject(error);
});

//定义axios的响应拦截器

Vue.axios.interceptors.response.use(
  (res) => {
      if(res.data.resultCode == 403){
          Vue.$router.push({
              name: 'register'
          })
      }else{
          return res;
      }
  },
  (error) => {
    // Do something with response error
    return Promise.reject(error);
  }
);


