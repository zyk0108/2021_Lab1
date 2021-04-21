package com.example.test.Service;

import com.example.test.Entity.AccountInfo;
import com.example.test.Entity.Bill;
import com.example.test.Entity.Config;
import com.example.test.Entity.LoanAccount;
import com.example.test.mapper.LoanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class LoanService {

    private final LoanMapper mapper;

    @Autowired
    public LoanService(LoanMapper mapper){
        this.mapper = mapper;
    }

    public List<LoanAccount> getLoanAccount(){
        return mapper.getAllLoanAccount();
    }

    public List<Bill> getBill(int customerCode){
        return mapper.getBillFromCustomerCode(customerCode);
    }

    public List<Bill> getNoPayBill(int customerCode){
        return mapper.getNoPayBill(customerCode);
    }


    public void payForFine(double fine, int customerCode, int periodNum, int loan_num){
        Bill bill = mapper.getBillFromPeriodNumAndCustomerCode(periodNum,customerCode);
        LoanAccount loanAccount = mapper.getLoanAccountFromCustomerCodeAndLoanNum(customerCode,loan_num);
        AccountInfo accountInfo = mapper.getAccountInfoFromCustomerCode("" + customerCode);
        BigDecimal toDecimal = new BigDecimal(String.valueOf(bill.getFine()));
        BigDecimal totalDecimal = new BigDecimal(String.valueOf(loanAccount.getFine()));
        BigDecimal fromDecimal = new BigDecimal(String.valueOf(fine));
        bill.setFine(toDecimal.subtract(fromDecimal).doubleValue());
        accountInfo.setFine(accountInfo.getFine()-(int)fine);
        loanAccount.setFine(totalDecimal.subtract(fromDecimal).doubleValue());
        mapper.updateFineForBill(bill.getFine(),bill.getPeriodNum(), bill.getCustomerCode());
        mapper.updateFineForLoanAccount(loanAccount.getFine(),loanAccount.getCustomerCode(),loanAccount.getLoan_num());
        mapper.updateFineForAccountInfo(accountInfo.getFine(), accountInfo.getAccountNum());
    }

    public void payForBillTotally(double payAmount, int customerCode, int periodNum, int loan_num){
        Bill bill = mapper.getBillFromPeriodNumAndCustomerCode(periodNum,customerCode);
        if(bill.getAlreadyFine().equals("no"))
            return;
        bill.setAlreadyPay("yes");
        AccountInfo accountInfo = mapper.getAccountInfoFromCustomerCode("" + bill.getCustomerCode());
        accountInfo.setLoan(accountInfo.getLoan() - (int)bill.getRemainingForPay());
        bill.setRemainingForPay(0);
        mapper.updateBill(bill);
        mapper.updateAccountInfo(accountInfo);
    }

    public void payForBillPartly(double payAmount, int customerCode, int periodNum, int loan_num){
        Bill bill = mapper.getBillFromPeriodNumAndCustomerCode(periodNum,customerCode);
        if(bill.getAlreadyFine().equals("no"))
            return;
        bill.setRemainingForPay(new BigDecimal(String.valueOf(bill.getRemainingForPay())).subtract(new BigDecimal(String.valueOf(payAmount))).doubleValue());
        mapper.updateBill(bill);
        AccountInfo accountInfo = mapper.getAccountInfoFromCustomerCode(""+bill.getCustomerCode());
        accountInfo.setLoan(accountInfo.getLoan() - (int)payAmount);
        mapper.updateAccountInfo(accountInfo);
    }

    public void dailyProcess(){
        List<Bill> bills = mapper.getAllBill();
        for(Bill bill : bills){
            processBill(bill);
        }
    }

    private void processBill(Bill bill){
        Date now = new Date();
        if(bill.getDueTime().before(now) && bill.getOverdue().equals("no") && bill.getAlreadyFine().equals("no")){
            bill.setOverdue("yes");
            bill.setFine(bill.getLoanAmount() * Config.PENALTY_RATE);
        }
        if(bill.getOverdue().equals("no"))
            return;
        if(bill.getAlreadyPay().equals("yes"))
            return;
        int customerId = mapper.getCustomerIdFromCustomerCode(bill.getCustomerCode());
        double remainAmount = mapper.getRemainAmountById(customerId);
        if(bill.getAlreadyFine().equals("no")){
            if(new BigDecimal(String.valueOf(remainAmount)).compareTo(new BigDecimal(String.valueOf(bill.getFine())))<0)
                return;
            else{
                remainAmount = new BigDecimal(String.valueOf(remainAmount)).subtract(new BigDecimal(String.valueOf(bill.getFine()))).doubleValue();
                bill.setFine(0);
                bill.setAlreadyFine("yes");
                mapper.updateFineForLoanAccount(0,bill.getCustomerCode(),bill.getLoan_num());
                mapper.updateFineForAccountInfo(0,bill.getCustomerCode()+"");
            }
        }
        if(new BigDecimal(String.valueOf(remainAmount)).compareTo(new BigDecimal(String.valueOf(bill.getRemainingForPay())))<0)
            return;
        else{
            remainAmount = new BigDecimal(String.valueOf(remainAmount)).subtract(new BigDecimal(String.valueOf(bill.getRemainingForPay()))).doubleValue();
            AccountInfo accountInfo = mapper.getAccountInfoFromCustomerCode(""+bill.getCustomerCode());
            accountInfo.setDeposit(accountInfo.getDeposit() - (int)bill.getRemainingForPay());
            bill.setRemainingForPay(0);
            bill.setAlreadyPay("yes");
            mapper.updateAccountInfo(accountInfo);
        }
        mapper.updateBill(bill);
        mapper.updateCard(remainAmount,customerId);
    }
}
