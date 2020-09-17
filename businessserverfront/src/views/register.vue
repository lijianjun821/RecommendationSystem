<template>
  <div class="login-box">
    <!-- 通过:rules="loginFormRules"来绑定输入内容的校验规则 -->
    <el-form :rules="loginFormRules" ref="loginForm" :model="loginForm" label-position="right" label-width="auto" show-message>
      <span class="login-title">欢迎注册</span>
      <div style="margin-top: 5px"></div>
      <el-form-item label="注册邮箱" prop="username">
        <el-col :span="22">
          <el-input type="text" v-model="loginForm.username"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="注册密码" prop="password">
        <el-col :span="22">
          <el-input type="password" v-model="loginForm.password"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loginSubmit('loginForm')">注册</el-button>
        <el-button type="primary" @click="restart('loginForm')">重置</el-button>
      </el-form-item>
    </el-form>
    <el-button type="text" @click="login()">用户登录</el-button>
  </div>
</template>
<script>
export default {
  name: 'register',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      // 表单验证，需要在 el-form-item 元素中增加 prop 属性
      loginFormRules: {
        username: [{ required: true, message: '账号不可为空', trigger: 'blur' }],
        password: [{ required: true, message: '密码不可为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    loginSubmit(formName) {
      // 为表单绑定验证功能
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$http.get('/rest/users/register', this.loginForm).then(res => {
            if (res.data.success) {
              this.$router.replace('/')
            } else {
              this.$message.error(res.data.message)
            }
          })
        } else {
          return false
        }
      })
    },
    restart() {
      this.loginForm = {}
    },
    login() {
      this.$router.replace('/')
    }
  }
}
</script>
<style scoped>
.login-box {
  border: 1px solid #dcdfe6;
  width: 350px;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px palegreen;
}
.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #66cd00;
  font-size: 30px;
  font-weight: bold;
}
</style>
