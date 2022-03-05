<template>
  <div>
    <el-container>
      <el-header class="HomeHeader">
        <div class="title">卡夫卡人事</div>
       <div>
         <el-button icon="el-icon-bell" type="text" style="margin-right: 8px;color: #a00202" size="normal" @click="goChat"></el-button>
         <el-dropdown class="userInfo" @command="commandHandler">
          <span class="el-dropdown-link">
            {{ user.name }}<i><img :src="user.userface"  alt=""/></i>
          </span>
           <el-dropdown-menu slot="dropdown">
             <el-dropdown-item command="userInfo">个人中心</el-dropdown-item>
             <el-dropdown-item command="setting">设置</el-dropdown-item>
             <el-dropdown-item command="logout" divided
             >注销登录
             </el-dropdown-item
             >
           </el-dropdown-menu>
         </el-dropdown>
       </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu router unique-opened>
            <el-submenu
                :index="index + ''"
                v-for="(item, index) in routes"
                v-if="!item.hidden"
                :key="index"
            >
              <template slot="title">
                <i :class="item.iconCls" style="color: #409EFF;margin-right: 2px"></i>
                <span>{{ item.name }}</span>
              </template>
              <el-menu-item
                  :index="child.path"
                  v-for="(child, indexj) in item.children"
                  :key="indexj"
              >{{ child.name }}
              </el-menu-item
              >
            </el-submenu>
          </el-menu>
        </el-aside>

        <el-main class="Main">
          <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ this.$router.currentRoute.name }}</el-breadcrumb-item>
          </el-breadcrumb>
          <div class="homeWelcome" v-if="this.$router.currentRoute.path=='/home'">
            欢迎来到我的人事!
          </div>
          <div class="img" v-if="this.$router.currentRoute.path=='/home'"></div>
          <router-view class="homeRouterView"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import {getRequest} from "../utils/api";

export default {
  name: "Home",
  data() {
    return {
      //user: JSON.parse(window.sessionStorage.getItem("user")),
    };
  },
  computed: {
    routes() {
      return this.$store.state.routes;
    },
    user(){
      return this.$store.state.currentHr;
    }
  },
  methods: {
    goChat(){
      this.$router.push('/chat')
    },
    // menuClick(index) {
    //   this.$router.push(index);
    // },
    commandHandler(cmd) {
      if (cmd == "logout") {
        this.$confirm("此操作将注销登录, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
            .then(() => {
              this.getRequest("/logout");
              window.sessionStorage.removeItem("user");
              this.$router.replace("/");
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消删除",
              });
            });
      }else if (cmd=='userInfo'){
        this.$router.push('/HrInfo')
      }
    },
  },
};
</script>

<style>
.img {
  width: 500px;
  height: 400px;
  margin-top: -90px ;
  background: url("../assets/survey.png") no-repeat;
  background-size: 100% 100%;

}

.homeRouterView {
  margin-top: 10px;
}

.homeWelcome {
  text-align: center;
  font-size: 30px;
  font-family: 华文行楷;
  color: #409eff;
  padding-top: 50px;


}

.HomeHeader {
  background-color: #409eff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0px 15px;
  box-sizing: border-box;
}

.HomeHeader .title {
  font-size: 30px;
  font-family: 华文新魏;
  color: whitesmoke;
}

.HomeHeader .userInfo {
  cursor: pointer;
}

.el-dropdown-link img {
  width: 48px;
  height: 48px;
  border-radius: 24px;
  margin-left: 8px;
}

.el-dropdown-link {
  display: flex;
  align-items: center;
  font-weight: 700;
}
</style>
