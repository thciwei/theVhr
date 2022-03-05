<template>
  <div>
    <div>
      <el-input
          size="small"
          style="width: 300px;margin-right: 10px"
          class="addPosInput"
          placeholder="添加职位"
          prefix-icon="el-icon-plus"
          v-model="pos.name"
          @keydown.enter.native="addPosition"
          v-loading="loading"
          element-loading-text="正在加载φ(゜▽゜*)♪"
          element-loading-spinner="el-icon-loading">
      </el-input>
      <el-button icon="el-icon-plus" size="small" type="primary" @click="addPosition"
      >添加
      </el-button>
    </div>
    <div class="posManaMain">
      <el-table
          :data="positions"
          border stripe
          size="medium"
          style="width: 70%"
          @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="id"
            label="编号"
            width="55">
        </el-table-column>
        <el-table-column
            prop="name"
            label="职位名称"
            width="150">
        </el-table-column>
        <el-table-column
            prop="createDate"
            label="创建时间"
            width="120">
        </el-table-column>
        <el-table-column
            label="是否启用">
          <template slot-scope="scope">
            <el-tag size="small" type="success" v-if="scope.row.enabled">已启用</el-tag>
            <el-tag size="small" type="danger" v-else>未启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            label="操作">
          <template slot-scope="scope">
            <el-button
                size="mini"
                @click="showEditView(scope.$index, scope.row)">编辑
            </el-button>
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button type="danger" size="small" @click="deleteMany" style="margin-top: 8px"
                 :disabled="multipleSelection.length==0">批量删除
      </el-button>
    </div>
    <el-dialog
        title="修改职位"
        :visible.sync="dialogVisible"
        width="30%"
    >
      <div>
        <el-tag>职位名称</el-tag>
        <el-input class="updatePosInput" size="small" v-model="updatePos.name"></el-input>
      </div>
      <div>
        <el-tag>是否启用</el-tag>
        <el-switch
            v-model="updatePos.enabled"
            active-text="开启"
            inactive-text="禁用"
            style="margin-left: 5px">
        </el-switch>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "PosMana",
  data() {
    return {
      loading: false,
      pos: {
        name: ''
      },
      dialogVisible: false,
      updatePos: {
        name: '',
        enabled: ''
      },
      positions: [],
      multipleSelection: []
    }
  },
  mounted() {
    //刷新表格信息，不用放在created
    this.initPosition();
  },
  methods: {
    deleteMany() {
      this.$confirm('此操作将永久删除【' + this.multipleSelection.length + '】条记录,是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = '?';
        this.multipleSelection.forEach(item => {
          ids += 'ids=' + item.id + '&';
        })
        this.deleteRequest("/system/basic/pos/" + ids).then(resp => {
          if (resp) {
            this.initPosition();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val);
    },
    addPosition() {
      //判断添加是否有数值，没有应该提示
      if (this.pos.name) {
        this.postRequest("/system/basic/pos/", this.pos).then(resp => {
          if (resp) {
            this.initPosition();
            //添加后字清除，添加成功之所以会自动弹出信息是因为我们的封装
            this.pos.name = '';
          }
        })
      } else {
        this.$message.error('职位名称不可以为空');
      }
    },
    showEditView(index, data) {
      // this.updatePos = data;
      Object.assign(this.updatePos, data);
      this.dialogVisible = true;
    },
    doUpdate() {
      this.putRequest("/system/basic/pos/", this.updatePos).then(resp => {
        if (resp) {
          this.initPosition();
          this.updatePos.name = '';
          this.dialogVisible = false;
        }

      })
    },
    handleDelete(index, data) {
      this.$confirm('此操作将永久删除【' + data.name + '】职位, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/system/basic/pos/" + data.id).then(resp => {
          if (resp) {
            this.initPosition();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    initPosition() {
      this.loading = true;
      this.getRequest("/system/basic/pos/").then(resp => {
        this.loading = false;
        if (resp) {
          this.positions = resp;
        }
      })
    }
  }
}
</script>

<style>
.updatePosInput {
  width: 200px;
  margin-left: 10px;
}

.addPosInput {
  width: 300px;
  margin-right: 8px;
}

.posManaMain {
  margin-top: 10px;
}
</style>