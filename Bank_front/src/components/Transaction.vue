<template>
  <div id="app">
    <!--隐藏的输入框，账号查询使用-->
    <el-dialog title="Please input the account" :visible.sync="searchAccountVisible">
      <el-form :model="form">
        <el-form-item label="Account:" :label-width="formLabelWidth">
          <el-input v-model="form.account" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="searchAccountVisible = false">Cancel</el-button>
        <el-button type="primary" @click="theFormLead()">Confirm</el-button>
      </div>
    </el-dialog>

    <!--购买的表单-->
    <el-dialog title="Please complete the form" :visible.sync="buyFormVisible">
      <el-form ref="form" :rules="buyFormRules" :model="buyForm" label-width="130px" size="mini">
        <el-form-item label="Your account" required>
          <el-input v-model="buyForm.account" autocomplete="on" v-bind:disabled="true"></el-input>
        </el-form-item>

        <el-form-item label="Product name" required>
          <el-input v-model="buyForm.productName" autocomplete="on" v-bind:disabled="true"></el-input>
        </el-form-item>

        <el-form-item label="Product price" required>
          <el-input v-model="buyForm.productPrice" autocomplete="on" v-bind:disabled="true"></el-input>
        </el-form-item>

        <el-form-item label="Product amount" prop="productNum" required>
          <el-input v-model="buyForm.productNum" ></el-input>
        </el-form-item>

        <el-form-item v-if="productTimeNeed" label="Product time" prop="productTime" required>
          <el-input v-model="buyForm.productTime" ></el-input>
        </el-form-item>
        <!--<el-form-item label="The time" required>
          <el-col :span="11">
            <el-date-picker type="date" placeholder="Select Date" v-model="buyForm.date" style="width: 100%;"></el-date-picker>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-time-picker placeholder="Select time" v-model="buyForm.time" style="width: 100%;"></el-time-picker>
          </el-col>
        </el-form-item>-->

        <el-form-item prop="time" label="The time" required>
          <el-date-picker type="date"
                          format="yyyy.MM.dd" value-format="yyyy.MM.dd"
                          :picker-options="pickerOptions"
                          placeholder="the time(now)" auto-complete="off"
                          v-model="buyForm.time" style="width: 100%">
          </el-date-picker>
        </el-form-item>

        <el-form-item size="mini">
          <el-button type="primary" @click="canBuyProduct()">Submit</el-button>
          <el-button @click="buyFormVisible=false">cancel</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


    <el-container style="height: 100%; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <div style="align-content: center">
          <img src="../assets/background/photo.jpg" style="border-radius: 50%;width: 60%;padding: 20px 20% 10px 20%">
          <!--<span>卡片名称</span>-->
          <div style="margin-bottom: 20px">Name: {{this.name}}</div>
        </div>
        <!--实现导航栏的默认开关-->
        <el-menu :default-openeds="['3','4']"><!--:default-openeds="['1','3']"-->
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
            <el-menu-item-group>
              <el-menu-item index="3-1" @click="selectBy(0)">查询筛选</el-menu-item>
            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="4">
            <template slot="title"><i class="el-icon-menu"></i>理财产品</template>
            <el-menu-item-group>
              <el-menu-item index="4-1" @click="searchAccountVisible = true, leadFlag=0">购买</el-menu-item>
              <el-menu-item index="4-2" @click="searchAccountVisible = true, leadFlag=0">查询</el-menu-item>
              <!--<el-menu-item index="4-1" @click="searchAccountVisible = true,form.account=null">购买</el-menu-item>
              <el-menu-item index="4-2" @click="searchAccountVisible = true,form.account=null">查询</el-menu-item>-->
            </el-menu-item-group>
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
                <el-button @click="checkLogOut()">
                  注销此账户（登出）
                </el-button>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>

        <el-main>
          <div v-show="showBoughtButton" align="left">
            <el-button type="primary" size="mini" @click="theBoughtShow()">查看此账号持仓情况及盈亏</el-button>
            <el-button type="primary" size="mini" @click="canBuyButtonShow()">查看此账号可以购买产品</el-button>
          </div>
          <div v-show="showProductsCanBuy">
            <el-row :gutter="12">
              <el-col :span="8" id="conferenceList_needAudit" v-for="product in productCanBuy" :key="product.id">
                <el-card class="box-card" style="text-align: left;margin: 5px 0px">
                  <div slot="header" class="clearfix">
                    <span>理财产品（可以购买）:</span>
                  </div>
                  <div class="text item">
                    <el-card class="box-card" style="text-align: left;margin: 5px 0px">
                      <div class="text item">
                        <p>ProductName:{{product}}</p>
                      </div>
                    </el-card>
                  </div>
                  <div  class="clearfix">
                    <div style="text-align: right; margin: 0">
                      <el-button type="primary" size="mini" @click="getBuyForm(product)">购买此产品</el-button>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row   >
          </div>
          <div v-show="showBoughtProductsDetail">
            <el-table
              :data="tableData"
              style="width: 100%">
              <el-table-column
                prop="account"
                label="账号"
                width="180">
              </el-table-column>
              <el-table-column
                prop="productName"
                label="产品名称"
                width="180">
              </el-table-column>
              <el-table-column
                prop="balance"
                label="盈亏状况">
              </el-table-column>
              <el-table-column
                prop="theTime"
                label="操作时间">
              </el-table-column>
            </el-table>
          </div>

          <!--流水信息-->
          <div v-show="showFlowButton" align="left">
            <el-button type="primary" size="mini" @click="clearFilter()">清除所有过滤器</el-button>
          </div>
          <div v-show="showFlowDetail">
            <el-table
              ref="filterTable"
              :data="flowData"
              style="width: 100%">
              <el-table-column
                prop="account"
                label="账号"
                width="180">
              </el-table-column>
              <el-table-column
                prop="date"
                label="日期"
                sortable
                width="180"
                column-key="date"
                :filters="[{text: '2021.06.10', value: '2021.06.10'}]"
                :filter-method="filterHandler">
              </el-table-column>

              <el-table-column
                prop="operation"
                label="相关操作"
                width="100"
                :filters="[{ text: '购买的记录', value: '购' }]"
                :filter-method="filterBuy">
              </el-table-column>

              <el-table-column
                prop="amount"
                label="涉及的金额"
                :filters="[{ text: '金额大于1000', value: 1000 }]"
                :filter-method="filterTag"
                filter-placement="bottom-end">
                <template slot-scope="scope">
                  <el-tag
                    :type="scope.row.amount > 1000 ? 'primary' : 'success'"
                    disable-transitions>{{scope.row.amount}}</el-tag>
                </template>
              </el-table-column>
            </el-table>
          </div>
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

        //弹出购买产品表单
        buyFormVisible:false,

        leadFlag:0,
        // 弹出账号搜索输入框
        searchAccountVisible: false,
        formLabelWidth:'120px',
        form:{
          account:'',
        },

        //购买产品,账号对应于form.account
        products:["股票","基金","定期"],
        grade:1, // 账户的等级
        productCanBuy:[], // 此账户可以买的产品
        productBuy:'', // 选择买的产品
        accountBalance:'',//账户的金额

        productId:'',//产品代号
        fine:0,//罚金的金额，不欠罚金为0
        productTimeNeed:false,
        //购买产品表单
        buyForm: {
          account:'',//具体的账号
          productName:'',
          productPrice:'',
          time:'',
          productNum:'',//购买的产品的数量，如股票数
          productTime:0,
          active:'active'
        },
        buyFormRules:{
          productNum: [
            { required: true, message: 'your productNum can not be null', trigger: 'blur' },
            { max: 10, message: 'the max length is 10', trigger: 'blur' },
          ],
          time: [
            {required: true, message: 'Select the time', trigger: 'blur'}
          ]
        },

        //盈亏
        tableData: [],
        //流水
        flowData: [],

        //时间
        pickerOptions: {
          disabledDate: (time) => {
            return time.getTime() < Date.now() - 8.64e7;
          }
        },

        //RE_4显示可以购买的产品的卡片
        showProductsCanBuy:false,
        showBoughtButton:false,
        showBoughtProductsDetail:false,//盈亏情况

        //RE_3显示可以购买的产品的卡片
        showFlowButton:false,
        showFlowDetail:false,//流水信息展示
        //Re_3
        selectCondition:0,
      }
    },

    methods:{
      //Re_3过滤筛选
      clearFilter() {
        this.$refs.filterTable.clearFilter();
      },
      filterTag(value, row) {
        return row.amount > value;
      },
      filterHandler(value, row, column) {
        const property = column['property'];
        return row[property] === value;
      },

      filterBuy(value, row){
        var c = row.operation[0]
        return c == value
      },

      //操作结果提示
      buySuccess() {
        this.$message({
          message: 'You have bought the product successfully.',
          type: 'success'
        });
      },


      //银行流水信息查询
      getAccountFlowInfo(){
        this.$axios.post('/getAccountFlowInfo',{
          account: this.form.account,
          token: localStorage.getItem("token")
        }).then((response) => {
          //如果请求成功
          if(response.status==200){
            let len=response.data.length
            console.log(len)

            this.flowData=[]
            for (let i = 0; i < len; i++) {
              this.flowData.push({account: response.data[i].account, date: response.data[i].date, operation: response.data[i].operation,amount:response.data[i].amount});
            }

            // //测试
            // this.flowData.push({account: response.data[0].account, date: '2021.04.10', operation: '购买233',amount:200});
            // this.flowData.push({account: response.data[0].account, date: '2021.04.12', operation: '33买233',amount:200456});
            // this.flowData.push({account: response.data[0].account, date: '2021.04.30', operation: '购买233',amount:207890});

            //关闭账号查询弹出框
            this.searchAccountVisible = false

          }
        }).catch((error) => {
          console.log(error);
          //invoke the function
          this.failure();
        });
      },

      //账号流水信息筛选
      selectBy(condition){
        this.searchAccountVisible = true
        this.leadFlag=1
        this.selectCondition=condition
      },



      //控制账号查询的弹窗导向
      theFormLead(){
        //0代表RE_4
        if (this.leadFlag == 0) {
          //用于购买
          this.getAccountGrade()
          //主界面其他显示关闭
          this.showFlowButton=false
          this.showFlowDetail=false
          this.showBoughtProductsDetail=false
          //开主界面
          this.showProductsCanBuy=true
          this.showBoughtButton=true
        }else {
          //用于账号流水
          this.getAccountFlowInfo()
          //主界面其他显示关闭
          this.showProductsCanBuy=false
          this.showBoughtButton=false
          this.showBoughtProductsDetail=false
          //开主界面
          this.showFlowButton=true
          this.showFlowDetail=true
        }
      },

      //控制持仓button显示
      theBoughtShow(){
        this.showProductsCanBuy=false
        this.checkData()
        this.showBoughtProductsDetail=true
      },

      //控制可以买button显示
      canBuyButtonShow(){
        this.showProductsCanBuy=true;
        this.showBoughtProductsDetail=false;
      },

      //查询盈亏
      checkData(){
        this.$axios.post('/checkData',{
          account:this.form.account,
          token: localStorage.getItem("token")
        }).then((response) => {
          //如果请求成功
          if(response.status==200){
            //将用户等级设置为返回的account
            let len=response.data.length
            console.log(len)

            let balance=''
            this.tableData=[]
            for (let i = 0; i < len; i++) {
              switch (response.data[i].condition) {
                case 0:
                  balance="亏损"
                  break;
                case 1:
                  balance="盈利"
              }
              this.tableData.push({account: response.data[i].account, productName: response.data[i].theProduct, balance: balance, theTime: response.data[i].date});
            }
            console.log(response.data)
          }
        }).catch((error) => {
          console.log(error);
          //invoke the function
          this.failure();
        });
      },

      // method for get the account grade
      getAccountGrade(){
        this.$axios.post('/getAccountGrade',{
          account:this.form.account,
          token: localStorage.getItem("token")
        }).then((response) => {
          //如果请求成功
          if(response.status==200){
            //将用户等级设置为返回的account
            this.grade=response.data.grade;
            switch (this.grade) {
              case 1:
                this.productCanBuy=this.products;
                break;
              case 2:
                this.productCanBuy=[]
                console.log(this.productCanBuy[0])
                this.productCanBuy.push(this.products[1]);
                this.productCanBuy.push(this.products[2]);
                break;
              case 3:
                this.productCanBuy=[]
                this.productCanBuy.push(this.products[2]);
            }
            this.searchAccountVisible = false
            console.log(response.data.grade)
            console.log(this.productCanBuy)
          }
        }).catch((error) => {
          console.log(error);
          //invoke the function
          this.failure();
          this.searchAccountVisible = false
        });
      },


      //弹出购买产品表单
      getBuyForm(theName){
        switch (theName) {
          case "股票":
            this.productTimeNeed=false
            this.productId=1;
            break;
          case "基金":
            this.productTimeNeed=true
            this.productId=2;
            break;
          case "定期":
            this.productTimeNeed=true
            this.productId=3;
        }
        //price undefined ???
        let price=this.getProductPrice(this.productId)
        console.log(price)
        this.buyForm.account=this.form.account
        this.buyForm.productName=theName

        this.buyFormVisible=true
      },

      //得到产品的价格
      getProductPrice(id){
        this.$axios.post("/getProductPrice/"+id,
          {
            token: localStorage.getItem("token")
          }
        ).then((resp)=>{
          console.log("getPrice")
          if (resp.status == 200) {
            this.buyForm.productPrice=resp.data.productPrice
          }
        }).catch((error)=>{
          console.log(error)
          this.failure()
        })
      },


      //判断是否有罚金，是否可以买产品
      canBuyProduct(){
        //得到账号余额
        this.getAccountBalance()
        this.$axios.post('/canBuyProduct',{
          account:this.form.account,
          token: localStorage.getItem("token")
        }).then((response) => {
          //如果请求成功
          if(response.status==200){
            this.fine=response.data.fine
            console.log(response.data)
            //coding here...
            if (response.data.flag == true) {
              console.log(this.accountBalance)
              console.log(this.buyForm.productPrice*this.buyForm.productNum)
              console.log(this.buyForm.productNum)
              if ((this.buyForm.productPrice*this.buyForm.productNum) > (this.accountBalance-response.data.fine)) {
                this.canNotBuy("your account balance is not enough because of the fine and the product you should pay.")
              }else {
                this.canBuy("your account balance is enough,and you should pay for the fine and the product price at one time.")
              }
            }else{
              console.log(this.accountBalance)
              console.log(this.buyForm.productPrice*this.buyForm.productNum)
              console.log(this.buyForm.productNum)
              //response.data.fine=0,所以有无均一样
              if ((this.buyForm.productPrice*this.buyForm.productNum) > (this.accountBalance-response.data.fine)) {
                this.canNotBuy("your account balance is not enough because of the product you should pay.")
              }else {
                this.canBuy("your account balance is enough,click confirm to ensure you will pay for the product.")
              }
            }
          }
        }).catch((error) => {
          console.log(error);
          //invoke the function
          this.failure();
        });
      },

      //得到账户的存储的金额
      getAccountBalance(){
        this.$axios.post('/getAccountBalance',{
          account:this.form.account,
          token: localStorage.getItem("token")
        }).then((response) => {
          //如果请求成功
          if(response.status==200){
            //coding here...
            this.accountBalance=response.data.accountBalance
            console.log(response.data)
          }
        }).catch((error) => {
          console.log(error);
          //invoke the function
          this.failure();
        });
      },

      //购买产品
      buyProduct(){
        this.$axios.post('/buyProduct',{
          account:this.form.account,
          productId:this.productId,//产品代号
          fine:this.fine,
          date:this.buyForm.time,
          time:this.buyForm.productTime,
          productNum:this.buyForm.productNum,
          token: localStorage.getItem("token")
        }).then((response) => {
          //如果请求成功
          if(response.status==200){
            //coding here...
            this.buyFormVisible=false
            this.buySuccess()
          }
        }).catch((error) => {
          console.log(error);
          //invoke the function
          this.failure();
        });
      },

      //判断是否确认购买产品
      canBuy(message) {
        this.$alert('Sure do the operation?'+message, 'Alert information:', {
          confirmButtonText: 'OK',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
            if(action == 'confirm')this.buyProduct();
          }
        });
      },

      canNotBuy(message) {
        this.$alert('You have failed the operation!'+message, 'Alert information:', {
          confirmButtonText: 'OK',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
          }
        });
      },


      failure() {
        this.$alert('You have failed the operation!', 'Alert information:', {
          confirmButtonText: 'OK',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
          }
        });
      },

      success() {
        this.$alert('You have done this successfully.', 'Alert info:', {
          confirmButtonText: 'OK',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
          }
        });
      },


      //登出账号：两个方法
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
