<template>
  <div class="login">
    <el-form
        :rules="rules"
        :model="loginForm"
        ref="loginForm"
        class="loginContainer"
        v-loading="loading"
        element-loading-text="正在登录φ(゜▽゜*)♪"
        element-loading-spinner="el-icon-loading"
    >
      <h3 class="loginTitle">系统登录</h3>
      <el-form-item prop="username">
        <el-input
            type="text"
            v-model="loginForm.username"
            auto-complete="off"
            placeholder="请输入用户名"
            size="normal"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
            v-model="loginForm.password"
            auto-complete="off"
            placeholder="请输入密码"
            @keydown.enter.native="submitLogin"
            show-password
            size="normal"
        ></el-input>
      </el-form-item>
      <!--      <el-checkbox v-model="checked" class="loginRemember" size="normal"></el-checkbox>-->
      <el-button type="primary" style="width: 100%;margin-bottom: 10px" @click="submitLogin" size="normal"
      >登录
      </el-button
      >
    </el-form>

  </div>
</template>

<script>
import {postKeyValueRequest} from "../utils/api";

export default {
  data() {
    return {
      loading: false,
      loginForm: {
        username: "admin",
        password: "123",
      },
      checked: true,
      rules: {
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"},
        ],
        password: [
          {required: true, message: "请输入用户名", trigger: "blur"},
        ],
      },
    };
  },
  methods: {
    submitLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          postKeyValueRequest("/doLogin", this.loginForm).then((resp) => {
            //关闭加载
            this.loading = false;
            if (resp) {
              this.$store.commit('INIT_CURRENTHR', resp.obj);
              window.sessionStorage.setItem("user", JSON.stringify(resp.obj));
              // 如果在地址栏中输入地址，登录后记住这个地址跳转
              let path = this.$route.query.redirect;
              // this.$router.replace("/home");
              this.$router.replace((path == '/' || path == undefined) ? "/home" : path);
              // alert(JSON.stringify(res));

            }
          });
          // alert("submit!");
        } else {
          console.log("请输入所有字段");
          return false;
        }
      });
    },
  },
};
</script>

<style>
.login{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/cool5.jpg");
  background-size: cover;
}
.loginContainer {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 100px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #ffffff;
  border: 1px solid #eaeaea;
  /*box-shadow: 0 0 25px #cac6c6;*/
}

.loginTitle {
  margin: 15px auto 30px auto;
  text-align: center;
  color: #505458;
}


</style>
