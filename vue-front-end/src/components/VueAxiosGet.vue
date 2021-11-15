<template>
  <div class="hello" id="app">
    <h1>{{ msg }}</h1>

    <h3>get请求</h3>
    <input v-model="url1" placeholder="url" />
    <button type="button" @click="getAjax1()">点击尝试</button>
    <h5>data: {{ addData1 }}</h5>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "VueResourceGet",
  data() {
    return {
      url1:
        "http://127.0.0.1:8081/web-a/get",
      addData1: {},
      msg: "演示GET请求",
    };
  },
  mounted() {
    //渲染页面时开始执行
    //this.getAjax();
    //this.getAjaxGinseng();
  },
  methods: {
    getAjax1: function () {
      //无参请求
      var successCallback = (response) => {
        console.log("服务器请求成功了");
        let data = response.data;
        console.log(data);
        this.addData1 = data;
      };
      var errorCallback = (response) => {
        console.log("服务器请求出错了");
      };
      axios.get(this.url1).then(successCallback, errorCallback);
    }
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
