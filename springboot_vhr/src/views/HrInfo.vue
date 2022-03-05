<template>
  <div>
    <el-card class="box-card" style="width: 400px">
      <div slot="header" class="clearfix">
        <span>{{ hr.name }}</span></div>
      <div>
        <div style="display: flex">
          <div>

            <el-upload
                :show-file-list="false"
                :on-success="onSuccess"
                :data="hr"
                action="/hr/userface">
              <img :src="hr.userface" style="width: 100px;height: 100px;border-radius: 50px" alt="">
            </el-upload>
            <span style=" color:rgba(0,0,0,0.4);text-align: center;padding-left: 15px;font-size: 8px;
">点击修改头像</span>
          </div>
          <div>
            <div>电话号码:
              <el-tag>{{ hr.telephone }}</el-tag>
            </div>
            <div> 手机号码:
              <el-tag>{{ hr.phone }}</el-tag>
            </div>
            <div> 用户地址:
              <el-tag>{{ hr.address }}</el-tag>
            </div>
            <div> 用户标签:
              <el-tag type="success" v-for="(item,index) in hr.roles" :key="index" style="margin-left: 2px">
                {{ item.nameZh }}
              </el-tag>
            </div>
          </div>
        </div>
        <div style="display: flex;justify-content: space-around;margin-top: 10px">
          <el-button type="primary" @click="showUpdateHrInfoView">修改信息</el-button>
          <el-button type="danger" @click="showUpdatePasswordView">修改密码</el-button>
        </div>
      </div>
    </el-card>
    <el-dialog
        title="修改信息"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
      <div>
        <table>
          <tr>
            <td>
              <el-tag>用户昵称:</el-tag>
            </td>
            <td>
              <el-input v-model="hr2.name"></el-input>
            </td>
          </tr>
          <tr>
            <td>
              <el-tag>电话号码:</el-tag>
            </td>
            <td>
              <el-input v-model="hr2.telephone"></el-input>
            </td>

          </tr>
          <tr>
            <td>
              <el-tag>手机号码:</el-tag>
            </td>
            <td>
              <el-input v-model="hr2.phone"></el-input>
            </td>

          </tr>
          <tr>
            <td>
              <el-tag>用户地址:</el-tag>
            </td>
            <td>
              <el-input v-model="hr2.address"></el-input>
            </td>

          </tr>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="updateHrInfo">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
        title="修改密码"
        :visible.sync="dialogPasswordVisible"
        width="30%"
        :before-close="handleClose">
      <div>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="请输入旧密码" prop="pass">
            <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="请输入新密码" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "HrInfo",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    return {
      hr: null,
      hr2: null,
      dialogVisible: false,
      dialogPasswordVisible: false,
      ruleForm: {
        oldPass: '',
        pass: '',
        checkPass: '',
        hrid: ''
      },
      rules: {
        oldPass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        pass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ],
      }
    }

  },
  mounted() {
    this.initHr()
  },
  methods: {
    onSuccess() {
      this.initHr();
    },
    updateHrInfo() {
      this.putRequest("/hr/info", this.hr2).then(resp => {
        if (resp) {
          this.dialogVisible = false;
          this.initHr()
        }
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.hrid = this.hr.id;
          this.putRequest("/hr/pass", this.ruleForm).then(resp => {
            if (resp) {
              this.getRequest("/logout");
              window.sessionStorage.removeItem("user");
              this.$store.commit('INIT_HR', [])
              this.$router.replace("/");
            }
          })
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    showUpdateHrInfoView() {
      this.dialogVisible = true

    },
    showUpdatePasswordView() {
      this.dialogPasswordVisible = true
    },
    initHr() {
      this.getRequest("/hr/info").then(resp => {
        if (resp) {
          this.hr = resp;
          //如果使用 this.hr2 = resp;指向的仍是同一块内存，仍然是共享数据源 与this.hr2=this.hr相同
          //拷贝
          this.hr2 = Object.assign({}, this.hr)
          //更新sessionStorage和vuex
          window.sessionStorage.setItem("user", JSON.stringify(resp));
          this.$store.commit('INIT_CURRENTHR', resp)
        }
      })
    }
  }
}
</script>

<style>

</style>
