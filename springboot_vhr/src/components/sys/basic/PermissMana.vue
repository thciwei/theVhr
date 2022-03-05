<template>
  <div
      v-loading="globalLoading"
      element-loading-text="正在添加(｡☉౪ ⊙｡)"
      element-loading-spinner="el-icon-loading">
    <div class="permissManaTool">
      <el-input placeholder="请输入角色英文名" size="small" v-model="role.name">
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input placeholder="请输入角色中文名" size="small" v-model="role.nameZh" @keydown.enter.native="doAddRole"></el-input>
      <el-button type="primary" size="small" icon="el-icon-plus" @click="doAddRole">添加角色</el-button>
    </div>
    <div class="permissManaMain">
      <el-collapse v-model="activeName" accordion @change="change">
        <el-collapse-item :title="r.nameZh" :name="r.id" v-for="(r,index) in roles" :key="index">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>可访问的资源</span>
              <el-button style="float: right; padding: 3px 0;color: red"
                         icon="el-icon-delete" type="text" @click="deleteRole(r)"></el-button>
            </div>
            <div>
              <el-tree :key="index" show-checkbox :data="allmenus" :props="defaultProps" node-key="id"
                       :default-checked-keys="selectedMenus" ref="tree"
              ></el-tree>
              <div style="display: flex;justify-content: flex-end">
                <el-button @click="cancelUpdate">取消修改</el-button>
                <el-button type="primary" @click="doUpdate(r.id,index)">确认修改</el-button>
              </div>
            </div>
          </el-card>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "PermissMana",
  data() {
    return {
      globalLoading: false,
      activeName: -1,
      role: {
        name: '',
        nameZh: '',
      },
      roles: [],
      allmenus: [],
      selectedMenus: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  mounted() {
    this.initRoles();
  },
  methods: {
    deleteRole(role) {
      this.$confirm('此操作将永久删除【' + role.nameZh + '】角色 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/system/basic/permiss/role/" + role.id).then(resp => {
          if (resp) {
            this.initRoles();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    doAddRole() {
      this.globalLoading = true;
      //如果字段有空
      if (this.role.name && this.role.nameZh) {
        this.postRequest("/system/basic/permiss/role", this.role).then(resp => {
          this.globalLoading = false;
          if (resp) {
            this.initRoles();
            this.role.name = '';
            this.role.nameZh = '';
          }
        })
      } else {
        this.$message.error("数据不可以为空")
      }

    },
    cancelUpdate() {
      //-1是面板都不展示
      this.activeName = -1;
    },
    doUpdate(rid, index) {
      //先打印角色id console.log(rid);
      let tree = this.$refs.tree[index];
      //true只返回叶子节点
      let selectKeys = tree.getCheckedKeys(true);
      //console.log(selectTrees)
      let url = '/system/basic/permiss/?rid=' + rid;
      selectKeys.forEach(key => {
        url += '&mids=' + key;
      })
      this.putRequest(url).then(resp => {
        if (resp) {
          //this.initRoles();
          this.activeName = -1;
        }
      })

    },
    change(rid) {
      //使用再加载
      if (rid) {
        this.initAllMenus()
        this.initSelectedMenus(rid);
      }
    },
    initSelectedMenus(rid) {
      this.getRequest("/system/basic/permiss/mids/" + rid).then(resp => {
        if (resp) {
          this.selectedMenus = resp;
        }
      })
    },
    initAllMenus() {
      this.getRequest("/system/basic/permiss/menus").then(resp => {
        if (resp) {
          this.allmenus = resp;
        }
      })
    },
    initRoles() {
      //调用getRoles的接口
      this.getRequest("/system/basic/permiss/").then(resp => {
        if (resp) {
          this.roles = resp;
        }
      })
    },
  }
}
</script>

<style>
.permissManaTool {
  /*水平排列*/
  display: flex;
  justify-content: flex-start;
}

.permissManaTool .el-input {
  width: 300px;
  margin-left: 3px;
  margin-right: 5px;
}

.permissManaMain {
  margin-top: 10px;
  width: 700px
}
</style>