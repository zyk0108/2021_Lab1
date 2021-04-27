<template>
  <div>
    <el-col :span="3">
      <el-input v-model="query"></el-input>
    </el-col>
    <el-col :span="3">
      <el-button>搜索</el-button>
    </el-col>
    <el-col :span="3">
      <el-button @click="dailyProcess">每日处理</el-button>
    </el-col>
    <el-col :span="4">
      <el-button @click="back">返回</el-button>
    </el-col>
    <el-table :data="loanAccountFilter" border>
      <el-table-column label="借据号" property="loan_num"></el-table-column>
      <el-table-column label="账号" property="customerCode"></el-table-column>
      <el-table-column label="名称" property="customerName"></el-table-column>
      <el-table-column label="创建日期" property="createTime"></el-table-column>
      <el-table-column label="贷款总额" property="totalLoanAmount"></el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <el-button @click="checkBill(scope.row)">CheckBill</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'LoanAccount',
  data(){
    return{
      query:'',
      loanAccount:[]
    }
  },
  created () {
    this.getAllLoanAccount();
  },
  computed:{
    loanAccountFilter(){
      const query = this.query.trim()
      if(query){
        return this.loanAccount.filter(value => (value.customerCode.indexOf(query)!==-1))
      }
      return this.loanAccount
    }
  },
  methods:{
    getAllLoanAccount(){
      this.$axios.post('/loan/getLoanAccount').then(resp => {
        if(resp.status===200 && resp.data){
          if(resp.data.message==='success'){
            this.loanAccount=resp.data.loanAccount
          }
        }
      }).catch(error =>{
        console.log(error)
      })
    },
    checkBill(row){
      this.$store.commit('setLoanAccount',row)
      this.$router.replace('/bill')
    },
    dailyProcess(){
      this.$axios.post('/loan/dailyProcess').then(resp=>{
        this.$message({
          type: 'success',
          message: '处理完成'
        })
      })
    },
    back(){
      this.$router.replace('/transaction')
    }
  }
}
</script>

<style scoped>

</style>
