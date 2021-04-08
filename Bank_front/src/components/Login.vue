<template>
  <div id="base_login">

    <el-form :model="loginForm"
             :rules="rules"
             class="login_container"
             label-position="left"
             label-width="0px"
             v-loading="loading">
      <h3 class="login_title">Login</h3>
      <el-form-item prop="username">
        <el-input type="text"
                  class="login_input"
                  prefix-icon="el-icon-user-solid"
                  v-model="loginForm.username"
                  auto-complete="off"
                  placeholder="username"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password"
                  class="login_input"
                  prefix-icon="el-icon-lock"
                  v-model="loginForm.password"
                  auto-complete="off"
                  placeholder="password"></el-input>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button type="primary"
                   style="margin:10px auto 0px auto;width: 100%;background: #afb4db;line-height: 0.8"
                   v-on:click="login">login</el-button>
      </el-form-item>
      <el-form-item style="width: 100%;margin: 0px auto">
        <router-link to="">
          Technical support from seventeen group. Login in
        </router-link>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: 'Login',
    data () {
      return {
        loginForm: {
          username: '',
          password: '',
          active:'active'
        },
        rules: {
          username: [{required: true, message: '请输入规范的用户名，5-32个字符，只包含字母数字或两种特殊字符（_、-）且只以字母或-开头.', trigger: 'blur'}],
          password: [{required: true, message: '6-32个字符，字母、数字或特殊字符（-、_）至少包含两种，不包含账号.', trigger: 'blur'}]
        },
        loading: false
      }
    },
    methods: {
      login () {
        this.$axios.post('/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        })
          .then(resp => {
            if (resp.status === 200 && resp.data.hasOwnProperty("token")) {
              this.$store.commit('login', resp.data)

              //当为管理员时跳转到审核界面
              if(resp.data.userDetails.role=='admin'){
                this.$router.replace({path: '/conferenceAudit'})
              }else {
                this.$router.replace({path: '/'})
              }
            } else{

              //console.log("fffffffffffffff");
              //console.log("ddddddddd",resp.data);
              this.check_login();
              //alert('login error')
            }
          })
          .catch(error => {
            console.log(error)

            //console.log("xxxxxxxxxxxxxxx");
            this.check_login();
            //alert('login error')
          })
      },

      check_login() {
        this.$alert('Please ensure the valid username and password!', 'Login error:', {
          confirmButtonText: 'OK',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
          }
        });
      }
    }
  }
</script>

<style scoped>
  @import url("https://unpkg.com/element-ui/lib/theme-chalk/index.css");

  #base_login{
    /*background: url("../assets/background/background.jpg") repeat;*/
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }
  body{
    margin: 0px;
    padding: 0px;
  }
  .login_container{
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .login_title {
    margin: 0px auto 30px auto;
    text-align: center;
    color: #494e8f;
  }

  /deep/ input{
    height:37px;
    margin-top: 10px;
  }
</style>
