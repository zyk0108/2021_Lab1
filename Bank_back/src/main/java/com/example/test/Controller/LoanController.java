package com.example.test.Controller;

import com.example.test.Entity.Bill;
import com.example.test.Entity.LoanAccount;
import com.example.test.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @Description:
 * 柜面归还贷款，自动归还贷款业务处理入口
 * @Author:
 * Xiaopeng Cen
 */
@RestController
@RequestMapping("/loan")
public class LoanController {


    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }
    /**
     * @Description:
     * 获取贷款账户
     * @param
     * @return
     */
    @PostMapping("/getLoanAccount")
    public ResponseEntity<?> getLoanAccount(){
        HashMap<String,Object> returnMap = new HashMap<>();
        List<LoanAccount> loanAccount = loanService.getLoanAccount();
        returnMap.put("message","success");
        returnMap.put("loanAccount",loanAccount);
        return ResponseEntity.ok(returnMap);
    }


    /**
     * @Description:
     * 获取某一个贷款账户的账单
     * @param map
     * @return
     */
    @PostMapping("/getBill")
    public ResponseEntity<?> getBill(@RequestBody HashMap<String,Object> map){
        Integer customerCode = (Integer) map.get("customerCode");
        List<Bill> bill = loanService.getBill(customerCode);
        HashMap<String,Object> returnMap = new HashMap<>();
        returnMap.put("message","success");
        returnMap.put("bills",bill);
        return ResponseEntity.ok(returnMap);
    }


    @PostMapping("/getNoPayBill")
    public ResponseEntity<?> getNoPayBill(@RequestBody HashMap<String,Object> map){
        Integer customerCode = (Integer) map.get("customerCode");
        List<Bill> noPayBill = loanService.getNoPayBill(customerCode);
        HashMap<String,Object> returnMap = new HashMap<>();
        returnMap.put("message","success");
        returnMap.put("bills",noPayBill);
        return ResponseEntity.ok(returnMap);
    }

    /**
     * @Description:
     * 还款(根据传输的operation参数的值决定是部分还款还是全部还款)
     * @param map
     * @return
     */
    @PostMapping("/repay")
    public ResponseEntity<?> repay(@RequestBody HashMap<String,Object> map){
        String operation = (String) map.get("operation");
        Integer loan_num = (Integer) map.get("loan_num");
        Integer customerCode = (Integer) map.get("customerCode");
        Integer periodNum = (Integer) map.get("periodNum");
        double payAmount = Double.parseDouble(map.get("payAmount").toString());
        if(operation.equals("totally")){
            loanService.payForBillTotally(payAmount,customerCode,periodNum,loan_num);
        }else{
            loanService.payForBillPartly(payAmount,customerCode,periodNum,loan_num);
        }
        HashMap<String,Object> returnMap = new HashMap<>();
        returnMap.put("message","success");
        return ResponseEntity.ok(returnMap);
    }


    /**
     * @Description:
     * 缴纳罚款(主动)
     * @param map
     * @return
     */
    @PostMapping("payForFine")
    public ResponseEntity<?> payForFine(@RequestBody HashMap<String,Object> map){
        double fine = Double.parseDouble(map.get("fine").toString());
        Integer customerCode = (Integer)map.get("customerCode");
        Integer periodNum = (Integer)map.get("periodNum");
        Integer loan_num = (Integer)map.get("loan_num");
        loanService.payForFine(fine,customerCode,periodNum,loan_num);
        HashMap<String,Object> returnMap = new HashMap<>();
        returnMap.put("message","success");
        return ResponseEntity.ok(returnMap);
    }


    /**
     * @Description:
     * 每日自动处理
     * @param
     * @return
     */
    @PostMapping("/dailyProcess")
    public ResponseEntity<?> dailyProcess(){
        loanService.dailyProcess();
        HashMap<String,Object> returnMap = new HashMap<>();
        returnMap.put("message","success");
        return ResponseEntity.ok(returnMap);
    }

}
