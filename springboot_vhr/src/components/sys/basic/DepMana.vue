<template>
  <div style="width: 500px">
    <el-input
        placeholder="请输入部门名称进行搜索...."
        v-model="filterText"
        prefix-icon="el-icon-search"
    >
    </el-input>

    <el-tree
        :data="deps"
        :props="defaultProps"
        :filter-node-method="filterNode"
        ref="tree">
       <span class="custom-tree-node" slot-scope="{ node, data }"
             style="display: flex;justify-content: space-between;width: 100%">
        <span>{{ data.name }}</span>
        <span>
          <el-button
              class="depBtn"
              type="primary"
              size="mini"
              @click="() => showAddDepView(data)">
           添加部门
          </el-button>
          <el-button
              class="depBtn"
              size="mini"
              type="danger"
              @click="() => deleteDep(data)">
            删除部门
          </el-button>
        </span>
      </span>
    </el-tree>
    <el-dialog
        title="添加部门"
        :visible.sync="dialogVisible"
        width="30%">
      <div>
        <table>
          <tr>
            <td>
              <el-tag>上级部门</el-tag>
            </td>
            <td>{{ pname }}</td>
          </tr>
          <tr>
            <td>
              <el-tag>部门名称</el-tag>
            </td>
            <td>
              <el-input v-model="dep.name" placeholder="请输入部门名称..."></el-input>
            </td>
          </tr>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addDep">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "DepMana",
  data() {
    return {
      dialogVisible: false,
      filterText: '',
      dep: {
        name: '',
        parentId: -1,
      },
      pname: '',
      deps: [],
      defaultProps: {
        children: 'children',
        label: 'name',
      }
    }
  },
  //监控filterText的变化
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  mounted() {
    this.initDeps();
  },
  methods: {
    addDep2Deps(deps, dep) {
      for (let i = 0; i < deps.length; i++) {
        let d = deps[i];
        if (d.id == dep.parentId) {
          //拼接
          d.children = d.children.concat(dep);
          if (d.children.length> 0) {
            d.parent = true;
          }
          return;
        } else {
          //递归
          this.addDep2Deps(d.children, dep)
        }
      }
    },
    initDep() {
      this.dep = {
        name: '',
        parentId: ''
      }
      this.pname = ''
    },
    addDep() {
      this.postRequest("/system/basic/department/", this.dep).then(resp => {
        if (resp) {
          this.addDep2Deps(this.deps, resp.obj);
          this.dialogVisible = false;
          //初始化变量
          this.initDep()
        }
      })
    },
    showAddDepView(data) {
      // console.log(data)
      this.pname = data.name;
      this.dep.parentId = data.id;
      this.dialogVisible = true;

    },
    removeDepFromDeps(p, deps, id) {
      for (let i = 0; i < deps.length; i++) {
        let d = deps[i];
        if (d.id == id) {
          //动态移除
          deps.splice(i, 1)
          if (deps.length == 0) {
            //不再是parent
            p.parent = false;
          }
          return;
        } else {
          this.removeDepFromDeps(d, d.children, id);
        }
      }
    },
    deleteDep(data) {
      // console.log(data);
      if (data.parent) {
        this.$message.error("父部门删除失败")
      } else {
        this.$confirm('此操作将永久删除该文件【' + data.name + '】 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/system/basic/department/" + data.id).then(resp => {
            if (resp) {
              //第一次调没有parent传值null
              this.removeDepFromDeps(null, this.deps, data.id);
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }

    },
    initDeps() {
      this.getRequest("/system/basic/department/").then(resp => {
        if (resp) {
          this.deps = resp;
        }
      })
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    }
  }
}
</script>

<style>
.depBtn {
  padding: 2px;
}
</style>