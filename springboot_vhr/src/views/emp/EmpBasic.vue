<template>
  <div>
    <div>
      <div style="display: flex;justify-content: space-between">
        <div>
          <el-input placeholder="请输入员工名进行搜索,可以回车进行搜索.." prefix-icon="el-icon-search"
                    clearable @clear="initEmps" style="margin-right: 5px;width: 300px" v-model="keyword"
                    @keydown.enter.native="initEmps" :disabled="showAdvanceSearchView"></el-input>
          <el-button icon="el-icon-search" type="primary" @click="initEmps" :disabled="showAdvanceSearchView">搜索
          </el-button>
          <el-button type="primary" @click="showAdvanceSearchView=!showAdvanceSearchView">
            <i :class="showAdvanceSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'"
               aria-hidden="true"></i>
            高级搜索
          </el-button>
        </div>

        <div>
          <el-upload
              style="display: inline-flex;margin-right: 8px"
              action="/emp/basic/import"
              multiple
              :show-file-list="false"
              :before-upload="beforeUpload"
              :on-success="onSuccess"
              :on-error="onError"
              :disabled="importDataDisabled">
            <el-button
                :disabled="importDataDisabled"
                type="success" :icon="importDataBtnIcon">
              {{ importDataBtnText }}
            </el-button>
          </el-upload>

          <el-button type="success" @click="exportData" icon="el-icon-download">
            导出数据
          </el-button>
          <el-button type="primary" icon="el-icon-plus" @click="showAddEmpView()">添加用户</el-button>
        </div>
      </div>
      <transition name="slide-fade">
        <div v-show="showAdvanceSearchView"
             style="border: 1px solid #409EFF;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0">
          <el-row>
            <el-col :span="5">
              政治面貌:
              <el-select v-model="searchValue.politicId" placeholder="请选择" size="mini" style="width: 130px">
                <el-option
                    v-for="item in politicsstatus"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              民族:
              <el-select v-model="searchValue.nationId" placeholder="请选择" size="mini" style="width: 100px">
                <el-option
                    v-for="item in nations"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              职位:
              <el-select v-model="searchValue.posId" placeholder="请选择职位" size="mini" style="width: 100px">
                <el-option
                    v-for="item in positions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              职称:
              <el-select v-model="searchValue.joblevelId" placeholder="请选择职称" size="mini" style="width: 100px">
                <el-option
                    v-for="item in joblevels"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="7">
              聘用形式:
              <el-radio-group v-model="searchValue.engageForm">
                <el-radio label="劳动合同">劳动合同</el-radio>
                <el-radio label="劳务合同">劳务合同</el-radio>
              </el-radio-group>
            </el-col>
          </el-row>
          <el-row style="margin-top: 10px">
            <el-col :span="5">
              所属部门:
              <el-popover
                  placement="right"
                  title="请选择部门"
                  width="200px"
                  trigger="manual"
                  v-model="popVisible2"
              >
                <el-tree :data="allDeps" :props="defaultProps" @node-click="searchViewHandleNodeClick"
                         default-expand-all></el-tree>
                <div
                    slot="reference"
                    style="width: 130px;display: inline-flex;
                    font-size: 13px;border: 1px solid #dedede;height: 26px;border-radius: 5px;
                    cursor:pointer;align-items: center;padding-left: 8px;box-sizing: border-box;margin-left: 1px"
                    @click="showDepView2">{{ inputDepName2 }}
                </div>
              </el-popover>
            </el-col>
            <el-col :span="10">
              入职日期:
              <el-date-picker
                  v-model="searchValue.beginDateScope"
                  type="daterange"
                  size="mini"
                  style="width: 300px"
                  unlink-panels
                  value-format="yyyy-MM-dd"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
              </el-date-picker>
            </el-col>
            <el-col :span="5" :offset="4">
              <el-button>取消</el-button>
              <el-button icon="el-icon-search" type="primary" @click="initEmps('advanced')">搜索</el-button>
            </el-col>
          </el-row>
        </div>
      </transition>
    </div>

    <div style="margin-top: 10px">
      <el-table
          :data="emps"
          stripe
          border
          style="width: 100%"
          v-loading="loading"
          element-loading-text="正在加载"
          element-loading-spinner="el-icon-loading">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            fixed
            prop="name"
            label="姓名"
            align="left"
            width="90">
        </el-table-column>
        <el-table-column
            prop="workId"
            label="工号"
            align="left"
            width="85">
        </el-table-column>
        <el-table-column
            prop="gender"
            label="性别"
            align="left"
            width="80">
        </el-table-column>
        <el-table-column
            prop="birthday"
            label="出生日期"
            align="left"
            width="90">
        </el-table-column>
        <el-table-column
            prop="idCard"
            label="身份证号"
            align="left"
            width="180">
        </el-table-column>
        <el-table-column
            prop="wedlock"
            label="婚姻情况"
            align="left"
            width="85">
        </el-table-column>
        <el-table-column
            prop="nation.name"
            label="民族"
            align="left"
            width="50">
        </el-table-column>
        <el-table-column
            prop="nativePlace"
            label="籍贯"
            align="left"
            width="80">
        </el-table-column>
        <el-table-column
            prop="politicsstatus.name"
            label="政治面貌"
            align="left"
            width="80">
        </el-table-column>
        <el-table-column
            prop="email"
            label="电子邮箱"
            align="left"
            width="180">
        </el-table-column>
        <el-table-column
            prop="workState"
            label="在职状态"
            align="left"
            width="70">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话号码"
            align="left"
            width="100">
        </el-table-column>
        <el-table-column
            prop="address"
            label="联系地址"
            align="left"
            width="220">
        </el-table-column>
        <el-table-column
            prop="department.name"
            label="所属部门"
            align="left"
            width="100">
        </el-table-column>
        <el-table-column
            prop="jobLevel.name"
            label="职称"
            align="left"
            width="100">
        </el-table-column>
        <el-table-column
            prop="position.name"
            label="职位"
            align="left"
            width="100">
        </el-table-column>
        <el-table-column
            prop="engageForm"
            label="聘用形式"
            align="left"
            width="100">
        </el-table-column>
        <el-table-column
            prop="tiptopDegree"
            label="最高学历"
            align="left">
        </el-table-column>

        <el-table-column
            prop="specialty"
            label="专业"
            align="left"
            width="150">
        </el-table-column>
        <el-table-column
            prop="school"
            label="毕业院校"
            align="left"
            width="150">
        </el-table-column>
        <el-table-column
            prop="beginDate"
            label="入职日期"
            align="left"
            width="100">
        </el-table-column>
        <el-table-column
            prop="conversionTime"
            label="转正日期"
            align="left"
            width="100">
        </el-table-column>
        <el-table-column
            prop="beginContract"
            label="合同起始日期"
            align="left"
            width="100">
        </el-table-column>
        <el-table-column
            prop="endContract"
            label="合同截止日期"
            align="left"
            width="100">
        </el-table-column>
        <el-table-column
            label="合同期限"
            align="left"
            width="100">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.contractTerm }}</el-tag>
            年
          </template>
        </el-table-column>

        <el-table-column
            fixed="right"
            width="200"
            label="操作">
          <template slot-scope="scope">
            <el-button style="padding:3px " size="mini" @click="showEditEmpView(scope.row)">编辑</el-button>
            <el-button style="padding:3px" size="mini" type="primary">查看高级资料</el-button>
            <el-button @click="deleteEmp(scope.row)" style="padding:3px" size="mini" type="danger">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="display: flex;justify-content: flex-end">
        <el-pagination
            background
            layout="sizes, prev, pager, next, jumper, ->, total, slot"
            :total="total"
            @current-change="currentChange"
            @size-change="sizeChange">
        </el-pagination>
      </div>
    </div>
    <el-dialog
        :title="title"
        :visible.sync="dialogVisible"
        width="80%">
      <div>
        <el-form :model="emp" :rules="rules" ref="empForm">
          <el-row>
            <el-col :span="6">
              <el-form-item label="姓名" prop="name">
                <el-input v-model="emp.name" size="mini" style="width: 150px" placeholder="请输入员工姓名"
                          prefix-icon="el-icon-edit">

                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="emp.gender">
                  <el-radio label="男">男</el-radio>
                  <el-radio label="女" style="margin-left: -20px">女</el-radio>
                </el-radio-group>
              </el-form-item>

            </el-col>
            <el-col :span="6">
              <el-form-item label="出生日期" prop="birthday">
                <el-date-picker
                    v-model="emp.birthday"
                    size="mini"
                    type="date"
                    placeholder="出生日期"
                    value-format="yyyy-MM-dd"
                    style="width: 150px">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="政治面貌" prop="politicId">
                <el-select v-model="emp.politicId" placeholder="请选择" size="mini" style="width: 200px">
                  <el-option
                      v-for="item in politicsstatus"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="民族" prop="nationId">
                <el-select v-model="emp.nationId" placeholder="请选择" size="mini" style="width: 150px">
                  <el-option
                      v-for="item in nations"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="籍贯" prop="nativePlace">
                <el-input v-model="emp.nativePlace" size="mini" style="width: 150px" placeholder="请输入籍贯"
                          prefix-icon="el-icon-edit">

                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="电子邮箱" prop="email">
                <el-input v-model="emp.email" size="mini" style="width: 150px" placeholder="电子邮箱"
                          prefix-icon="el-icon-message">

                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="联系地址" prop="address">
                <el-input v-model="emp.address" size="mini" style="width: 200px" placeholder="联系地址"
                          prefix-icon="el-icon-edit">

                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="职位" prop="posId">
                <el-select v-model="emp.posId" placeholder="请选择职位" size="mini" style="width: 150px">
                  <el-option
                      v-for="item in positions"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="职称" prop="joblevelId">
                <el-select v-model="emp.joblevelId" placeholder="请选择职称" size="mini" style="width: 150px">
                  <el-option
                      v-for="item in joblevels"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="所属部门" prop="departmentId">
                <el-popover
                    placement="right"
                    title="请选择部门"
                    width="200px"
                    trigger="manual"
                    v-model="popVisible"
                >
                  <el-tree :data="allDeps" :props="defaultProps" @node-click="handleNodeClick"
                           default-expand-all></el-tree>
                  <div
                      slot="reference"
                      style="width: 150px;display: inline-flex;
                    font-size: 13px;border: 1px solid #dedede;height: 26px;border-radius: 5px;
                    cursor:pointer;align-items: center;padding-left: 8px;box-sizing: border-box"
                      @click="showDepView">
                    {{ inputDepName }}
                  </div>
                </el-popover>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="电话号码" prop="phone">
                <el-input v-model="emp.phone" size="mini" style="width: 200px" placeholder="电话号码"
                          prefix-icon="el-icon-phone">

                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="工号" prop="workId">
                <el-input v-model="emp.workId" size="mini" style="width: 150px" placeholder="工号"
                          prefix-icon="el-icon-edit" disabled>

                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="学历" prop="tiptopDegree">
                <el-select v-model="emp.tiptopDegree" placeholder="请选择学历" size="mini" style="width: 150px">
                  <el-option
                      v-for="item in tiptopDegrees"
                      :key="item"
                      :label="item"
                      :value="item">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="毕业院校" prop="school">
                <el-input v-model="emp.school" size="mini" style="width: 150px" placeholder="学校"
                          prefix-icon="el-icon-school">

                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="专业名称" prop="specialty">
                <el-input v-model="emp.specialty" size="mini" style="width: 200px" placeholder="专业名称"
                          prefix-icon="el-icon-edit">

                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="入职日期" prop="beginDate">
                <el-date-picker
                    v-model="emp.beginDate"
                    size="mini"
                    type="date"
                    placeholder="入职日期"
                    value-format="yyyy-MM-dd"
                    style="width: 130px">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="转正日期" prop="conversionTime">
                <el-date-picker
                    v-model="emp.conversionTime"
                    size="mini"
                    type="date"
                    placeholder="合同起始日期"
                    value-format="yyyy-MM-dd"
                    style="width: 130px">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="合同起始日期" prop="beginContract">
                <el-date-picker
                    v-model="emp.beginContract"
                    size="mini"
                    type="date"
                    placeholder="合同起始日期"
                    value-format="yyyy-MM-dd"
                    style="width: 130px">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="合同终止日期" prop="endContract">
                <el-date-picker
                    v-model="emp.endContract"
                    size="mini"
                    type="date"
                    placeholder="合同起始日期"
                    value-format="yyyy-MM-dd"
                    style="width: 130px">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="身份证号码" prop="idCard">
                <el-input v-model="emp.idCard" size="mini" style="width: 200px" placeholder="请输入身份证号码"
                          prefix-icon="el-icon-edit">

                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="合同" prop="engageForm">
                <el-radio-group v-model="emp.engageForm">
                  <el-radio label="劳动合同">劳动合同</el-radio>
                  <el-radio label="劳务合同">劳务合同</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="婚姻状况" prop="wedlock">
                <el-radio-group v-model="emp.wedlock">
                  <el-radio label="已婚">已婚</el-radio>
                  <el-radio label="未婚" style="margin-left: -20px">未婚</el-radio>
                  <el-radio label="离异" style="margin-left: -20px">离异</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddEmp">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "EmpBasic",
  data() {
    return {
      searchValue: {
        nationId: null,
        politicId: null,
        engageForm: null,
        joblevelId: null,
        departmentId: null,
        posId: null,
        beginDateScope: null,
        eDate: null
      },
      keyword: '',
      title: '',
      importDataBtnText: '导入数据',
      importDataBtnIcon: 'el-icon-upload2',
      importDataDisabled: false,
      showAdvanceSearchView: false,
      allDeps: [],
      popVisible: false,
      popVisible2: false,
      nations: [],
      joblevels: [],
      politicsstatus: [],
      positions: [],
      tiptopDegrees: ['本科', '大专', '博士', '硕士', '高中', '初中', '小学', '其他'],
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      value: '',
      dialogVisible: false,
      loading: false,
      emps: [],
      total: 0,
      page: 1,
      size: 10,
      inputDepName: '所属部门',
      inputDepName2: '所属部门',
      emp: {
        name: "ciwei",
        gender: "男",
        birthday: "1990-03-21",
        idCard: "610122199001011256",
        wedlock: "已婚",
        nationId: 1,
        nativePlace: "陕西",
        politicId: 13,
        email: "laowang@qq.com",
        phone: "18565558897",
        address: "深圳市南山区",
        departmentId: null,
        joblevelId: 9,
        posId: 29,
        engageForm: "劳务合同",
        tiptopDegree: "本科",
        specialty: "信息管理与信息系统",
        school: "深圳大学",
        beginDate: "2018-01-01",
        workState: "在职",
        workId: "",
        contractTerm: 2.0,
        conversionTime: "2018-04-01",
        notworkDate: null,
        beginContract: "2018-01-01",
        endContract: "2020-01-01",
        workAge: null
      },
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      rules: {
        name: [{required: true, message: '请输入名字', trigger: 'blur'}],
        gender: [{required: true, message: '请输入性别', trigger: 'blur'}],
        birthday: [{required: true, message: '请输入出生日期', trigger: 'blur'}],
        idCard: [{required: true, message: '请输入身份证', trigger: 'blur'}, {
          pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)/,
          message: '身份证格式不正确',
          trigger: ['blur', 'change']
        }],
        wedlock: [{required: true, message: '请输入婚姻状况', trigger: 'blur'}],
        nationId: [{required: true, message: '请输入民族', trigger: 'blur'}],
        nativePlace: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
        politicId: [{required: true, message: '请输入政治面貌', trigger: 'blur'}],
        email: [{required: true, message: '请输入邮箱', trigger: 'blur'}, {
          type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']
        }],
        phone: [{required: true, message: '请输入联系电话', trigger: 'blur'}],
        address: [{required: true, message: '请输入家庭住址', trigger: 'blur'}],
        departmentId: [{required: true, message: '请输入部门', trigger: 'blur'}],
        joblevelId: [{required: true, message: '请输入职位', trigger: 'blur'}],
        posId: [{required: true, message: '请输入职称', trigger: 'blur'}],
        engageForm: [{required: true, message: '请输入聘用形式', trigger: 'blur'}],
        tiptopDegree: [{required: true, message: '请输入学历', trigger: 'blur'}],
        specialty: [{required: true, message: '请输入专业', trigger: 'blur'}],
        school: [{required: true, message: '请输入学校', trigger: 'blur'}],
        beginDate: [{required: true, message: '请输入入职日期', trigger: 'blur'}],
        workState: [{required: true, message: '请输入出生日期', trigger: 'blur'}],
        workId: [{required: true, message: '请输入工号', trigger: 'blur'}],
        contractTerm: [{required: true, message: '请输入合同期限', trigger: 'blur'}],
        conversionTime: [{required: true, message: '请输入转正日期', trigger: 'blur'}],
        notworkDate: [{required: true, message: '请输入离职日期', trigger: 'blur'}],
        beginContract: [{required: true, message: '请输入合同起始日期', trigger: 'blur'}],
        endContract: [{required: true, message: '请输入合同结束日期', trigger: 'blur'}],
        workAge: [{required: true, message: '请输入工龄', trigger: 'blur'}],
      }
    }
  },
  mounted() {
    this.initEmps();
    this.initData();
    this.initPosition();
  },
  methods: {
    searchViewHandleNodeClick(data) {
      this.inputDepName2 = data.name;
      this.searchValue.departmentId = data.id;
      this.popVisible2 = !this.popVisible2;
    },
    onError(err, file, fileList) {
      this.importDataBtnText = '导入数据';
      this.importDataBtnIcon = 'el-icon-upload2';
      this.importDataDisabled = false;
    },
    onSuccess(resp, file, fileList) {
      this.importDataBtnText = '导入数据';
      this.importDataBtnIcon = 'el-icon-upload2';
      this.importDataDisabled = false;
      this.initEmps();//导入后刷新表格数据
    },
    beforeUpload() {
      this.importDataBtnText = '正在导入';
      this.importDataBtnIcon = 'el-icon-loading';
      this.importDataDisabled = true;
    },
    exportData() {
      window.open('/emp/basic/export', '_parent');
    },
    //添加时置员工信息
    emptyEmp() {
      this.emp = {
        name: "",
        gender: "",
        birthday: "",
        idCard: "",
        wedlock: "",
        nationId: 1,
        nativePlace: "",
        politicId: 13,
        email: "",
        phone: "",
        address: "",
        departmentId: null,
        joblevelId: 9,
        posId: 29,
        engageForm: "",
        tiptopDegree: "",
        specialty: "",
        school: "",
        beginDate: "",
        workId: "",
        contractTerm: 2.0,
        conversionTime: "",
        notworkDate: null,
        beginContract: "",
        endContract: "",
        workAge: null
      };

      this.inputDepName = '';
    },
    //编辑员工
    showEditEmpView(data) {
      // console.log(data.id) console.log(data)
      this.initPosition();
      this.title = "编辑员工信息";
      this.emp = data;
      this.inputDepName = data.department.name;
      this.dialogVisible = true;
    },
    deleteEmp(data) {
      console.log(data)
      this.$confirm('此操作将永久删除【' + data.name + ' 】是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/emp/basic/" + data.id).then(resp => {
          if (resp) {
            this.initEmps();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    doAddEmp() {
      if (this.emp.id) {
        //如果有emp.id 则认为编辑
        this.$refs['empForm'].validate((valid) => {
          if (valid) {
            this.putRequest("/emp/basic/", this.emp).then(resp => {
              if (resp) {
                this.dialogVisible = false;
                this.initEmps();
              }
            })

          }
        })
      } else {
        //否则是添加
        this.$refs['empForm'].validate((valid) => {
          if (valid) {
            this.postRequest("/emp/basic/", this.emp).then(resp => {
              if (resp) {
                this.dialogVisible = false;
                this.initEmps();
              }
            })

          }
        })
      }


    },
    handleNodeClick(data) {
      this.inputDepName = data.name;
      this.emp.departmentId = data.id;
      this.popVisible = !this.popVisible;

    },
    //展示部门树
    showDepView() {
      this.popVisible = !this.popVisible;
    },
    showDepView2() {
      this.popVisible2 = !this.popVisible2;
    },
    getMaxWorkID() {
      this.getRequest("/emp/basic/maxWorkID").then(resp => {
        if (resp) {
          this.emp.workId = resp.obj;
        }
      })
    },
    //职位
    initPosition() {
      this.getRequest("/emp/basic/positions").then(resp => {
        if (resp) {
          this.positions = resp;
        }
      })
    },
    initData() {
      if (!window.sessionStorage.getItem("nations")) {
        this.getRequest("/emp/basic/nations").then(resp => {
          if (resp) {
            this.nations = resp;
            window.sessionStorage.setItem("nations", JSON.stringify(resp))
          }
        })
      } else {
        this.nations = JSON.parse(window.sessionStorage.getItem("nations"))
      }
      if (!window.sessionStorage.getItem("joblevels")) {
        this.getRequest("/emp/basic/joblevels").then(resp => {
          if (resp) {
            this.joblevels = resp;
            window.sessionStorage.setItem("joblevels", JSON.stringify(resp))
          }
        })
      } else {
        this.joblevels = JSON.parse(window.sessionStorage.getItem("joblevels"))
      }
      if (!window.sessionStorage.getItem("politicsstatus")) {
        this.getRequest("/emp/basic/politicsstatus").then(resp => {
          if (resp) {
            this.politicsstatus = resp;
            window.sessionStorage.setItem("politicsstatus", JSON.stringify(resp))
          }
        })
      } else {
        this.politicsstatus = JSON.parse(window.sessionStorage.getItem("politicsstatus"))
      }
      if (!window.sessionStorage.getItem("deps")) {
        this.getRequest("/emp/basic/dept").then(resp => {
          if (resp) {
            this.politicsstatus = resp;
            window.sessionStorage.setItem("deps", JSON.stringify(resp))
          }
        })
      } else {
        this.allDeps = JSON.parse(window.sessionStorage.getItem("deps"))
      }
    },
    showAddEmpView() {
      this.title = "添加员工";
      this.emptyEmp();
      this.getMaxWorkID();
      this.dialogVisible = true;
    },
    sizeChange(currentSize) {
      this.size = currentSize;
      this.initEmps();
    },
    currentChange(currentPage) {
      this.page = currentPage;
      this.initEmps('advanced');
    },
    initEmps(type) {
      this.loading = true;
      let url = '/emp/basic/?page=' + this.page + '&size=' + this.size;
      if (type && type == 'advanced') {
        if (this.searchValue.politicId) {
          url += '&politicId=' + this.searchValue.politicId;
        }
        if (this.searchValue.nationId) {
          url += '&nationId=' + this.searchValue.nationId;
        }
        if (this.searchValue.joblevelId) {
          url += '&jobLevelId=' + this.searchValue.joblevelId;
        }
        if (this.searchValue.posId) {
          url += '&posId=' + this.searchValue.posId;
        }
        if (this.searchValue.engageForm) {
          url += '&engageForm=' + this.searchValue.engageForm;
        }
        if (this.searchValue.departmentId) {
          url += '&departmentId=' + this.searchValue.departmentId;
        }
        if (this.searchValue.beginDateScope) {
          url += '&beginDateScope=' + this.searchValue.beginDateScope;
        }
      } else {
        url += '&name=' + this.keyword
      }
      this.getRequest(url).then(resp => {
        this.loading = false;
        if (resp) {
          this.emps = resp.data;
          this.total = resp.total;
        }
      })


    }
  }
};
</script>

<style>
.slide-fade-enter-active {
  transition: all .8s ease;
}

.slide-fade-leave-active {
  transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}

.slide-fade-enter, .slide-fade-leave-to
  /* .slide-fade-leave-active for below version 2.1.8 */
{
  transform: translateX(10px);
  opacity: 0;
}
</style>
