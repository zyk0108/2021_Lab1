<template>
  <div id="app">
    <el-container style="height: 100%; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <div style="align-content: center">
          <img src="../assets/background/photo.jpg" style="border-radius: 50%;width: 60%;padding: 20px 20% 10px 20%">
          <!--<span>卡片名称</span>-->
          <div style="margin-bottom: 20px">Name: {{this.name}}</div>
        </div>
        <!--实现导航栏的默认开关-->
        <el-menu ><!--:default-openeds="['1','3']"-->
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-menu"></i>柜面还款</template>
            <el-menu-item-group>
              <!--<el-menu-item index="1-1">1</el-menu-item>
              <el-menu-item index="1-2">2</el-menu-item>
              <el-menu-item index="1-3">3</el-menu-item>-->
            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i>自动还款</template>
          </el-submenu>

          <el-submenu index="3">
            <template slot="title"><i class="el-icon-menu"></i>查看流水</template>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px;background-color: #B3C0D1;
                    color: #333;line-height: 60px;padding:0 50px">
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px;cursor: pointer;">关于此账户</i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item >
                <el-button @click="checkLogOut();">
                  注销此账户（登出）
                </el-button>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>

        <el-main>

          <el-row :gutter="12">
            <el-col :span="8" id="conferenceList_needAudit" v-for="cashLists in cashList" :key="cashLists.id">
              <el-card class="box-card" style="text-align: left;margin: 5px 0px">
                <div slot="header" class="clearfix">
                  <span>Conference information:</span>
                </div>
                <div class="text item">
                  <el-card class="box-card" style="text-align: left;margin: 5px 0px">
                    <div class="text item">
                      <p>ShortName:{{cashLists.shortname}}<p>
                      <p>FullName: {{cashLists.fullname}}</p>
                      <p>StartTime:{{cashLists.starttime}}</p>
                      <p>EndTime:{{cashLists.endtime}}</p>
                      <p>Address:{{cashLists.address}}</p>
                      <p>Deadline for submission: {{cashLists.ddl}}</p>
                      <p>ReleaseTime:{{cashLists.releaseTime}}</p>
                    </div>
                  </el-card>
                </div>
                <div  class="clearfix">
                  <!--<div style="text-align: right; margin: 0">
                    <el-button v-if="cashLists.condition==0" type="primary" size="mini" @click="/////">###</el-button>
                  </div>-->
                </div>
              </el-card>
            </el-col>

          </el-row>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


<script>
  export default {
    name: 'Transaction',
    data() {
      return{
        name:localStorage.getItem("username"),
        visible:false,

        cashList: [],   //列表数组(现在是准备请求接口，不需要模拟的数据，所以设置一个空数组)
        topicList:[],
      }
    },

    methods:{
      getConferencesInformation(){
        this.$axios.post('/xxx',{
          token: localStorage.getItem("token")
        }).then((response) => {
          //如果请求成功了，这接口code为0代表请求成功。具体怎样判断还需要看接口
          if(response.status==200){
            //设置列表数据
            //coding here...

          }
        }).catch((error) => {
          console.log(error);
          //invoke the function
          this.conferenceAuditQuitWrong();
        });
      },


      failure_conferenceAudit() {
        this.$alert('You have failed the conference successfully!', 'Audit the conference:', {
          confirmButtonText: 'OK',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
          }
        });
      },

      success_conferenceAudit() {
        this.$alert('You have passed the conference successfully.', 'Audit the conference:', {
          confirmButtonText: 'OK',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
          }
        });
      },

      //注销账号
      logoutAccount() {
        // 移除token
        localStorage.removeItem('token')
        localStorage.removeItem('expireTime')
        localStorage.removeItem('username')
        this.$router.replace({path: '/'})
      },

      checkLogOut() {
        this.$alert('Are you sure to logout ?', 'Confirm', {
          confirmButtonText: 'OK',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
            if(action == 'confirm')this.logoutAccount();
          }
        });
      },
    },


    //methods 函数提前定义（ 类似提前声明变量 进入页面内容全部渲染完成后自动引函数）
    mounted()
    {
      //
      this.name=localStorage.getItem("username")
    },

  }
</script>

<style scoped>
  @import url("https://unpkg.com/element-ui/lib/theme-chalk/index.css");

  .text {
    font-size: 14px;
  }
  .item {
    margin-bottom: 18px;
  }
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
</style>
