<template>
  <div>
    <el-radio :label="1" v-model="typeOfBill">全部账单</el-radio>
    <el-radio :label="2" v-model="typeOfBill">未付账单</el-radio>
    <el-button @click="back2">返回</el-button>
    <el-table :data="billFilter">
      <el-table-column label="账号" property="customerCode"></el-table-column>
      <el-table-column label="到期时间" property="dueTime"></el-table-column>
      <el-table-column label="是否逾期" property="overdue"></el-table-column>
      <el-table-column label="待还金额" property="remainingForPay"></el-table-column>
      <el-table-column label="是否还款" property="alreadyPay"></el-table-column>
      <el-table-column label="罚款" property="fine"></el-table-column>
      <el-table-column label="缴纳罚款">
        <template slot-scope="scope">
          <el-button @click="payFine(scope.row)">缴纳罚款</el-button>
        </template>
      </el-table-column>
      <el-table-column label="还款">
        <template slot-scope="scope">
          <el-button @click="repayForPartly(scope.row)">部分还款</el-button>
          <el-button @click="repayForTotally(scope.row)">全部还款</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="payVisible" width="30%">
      <el-input v-model="money"></el-input>
      <el-button @click="pay()">确认</el-button>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Bill',
  data(){
    return{
      bill:[],
      typeOfBill: 1,
      remainingForPay: 0,
      payVisible: false,
      money:0,
      billRow:{}
    }
  },
  created () {
    this.getBill()
  },
  computed:{
    billFilter(){
      const query = this.typeOfBill
      if(query===2){
        return this.bill.filter(value => (value.alreadyPay==='no'))
      }
      return this.bill
    }
  },
  methods:{
    getBill(){
      this.$axios.post('/loan/getBill',{
        customerCode: this.$store.state.loanAccount.customerCode
      }).then(resp=>{
        this.bill = resp.data.bills
      })
    },
    payFine(row){
      if(row.fine===0){
        this.$message({
          type: 'warning',
          message: '没有罚金'
        })
        return
      }
      this.$axios.post('/loan/payForFine',{
        fine:row.fine,
        customerCode: row.customerCode,
        periodNum: row.periodNum,
        loan_num:this.$store.state.loanAccount.loan_num
      }).then(resp=>{
        this.getBill()
      })
    },
    repayForPartly(row){
      if(row.remainingForPay === 0){
        this.$message({
          type: 'warning',
          message: '已还款'
        })
        return
      }
      this.remainingForPay = row.remainingForPay
      this.billRow = row
      this.payVisible = true
    },
    repayForTotally(row){

      if(row.remainingForPay === 0){
        this.$message({
          type: 'warning',
          message: '已还款'
        })
        return
      }
      this.$axios.post('/loan/repay',{
        operation: 'totally',
        loan_num: this.$store.state.loanAccount.loan_num,
        customerCode: row.customerCode,
        periodNum: row.periodNum,
        payAmount:row.remainingForPay
      }).then(resp=>{
        this.getBill()
      })
    },
    pay(){
      if(this.money===0){
        this.$message({
          type: 'warning',
          message: '不能为0'
        })
      }else if(this.money > this.remainingForPay){
        this.$message({
          type: 'warning',
          message: '超过欠款'
        })
      }else{
        this.$axios.post('/loan/repay',{
          operation: 'partly',
          loan_num: this.$store.state.loanAccount.loan_num,
          customerCode: this.billRow.customerCode,
          periodNum: this.billRow.periodNum,
          payAmount:this.money
        }).then(resp=>{
          this.getBill()
          this.payVisible = false
        })
      }
    },
    back2(){
      this.$router.replace('/loanAccount')
    }
  }
}
</script>

<style scoped>

</style>
