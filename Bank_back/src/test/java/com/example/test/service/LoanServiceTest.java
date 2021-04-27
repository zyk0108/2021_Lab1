package com.example.test.service;

import com.example.test.TestApplication;
import com.example.test.entity.Bill;
import com.example.test.entity.LoanAccount;
import com.example.test.mapper.LoanMapper;
import org.apache.tomcat.jni.Lock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class LoanServiceTest {

    @Autowired private LoanService loanService;
    @Autowired private LoanMapper loanMapper;

    @Before
    public void before(){

    }

    @Test
    @Order(1)
    void getLoanAccount() {
        List<LoanAccount> list = loanService.getLoanAccount();
        Assert.assertEquals(list.size(),9);
    }

    @Test
    @Order(2)
    void getBill() {
        List<Bill> list = loanService.getBill(6);
        Assert.assertEquals(2,list.size());
        List<Bill> list2 = loanService.getBill(15);
        Assert.assertEquals(0,list2.size());
    }

    @Test
    @Order(3)
    void getNoPayBill() {
        List<Bill> list = loanService.getNoPayBill(6);
        Assert.assertEquals(2,list.size());
    }

    @Test
    @Order(4)
    void payForFine() {
        loanService.payForFine(250.0,6,1,6);
        Assert.assertEquals(0,loanMapper.getLoanAccountFromCustomerCodeAndLoanNum(6,6).getFine(),0);
        Assert.assertEquals("yes",loanMapper.getBillFromPeriodNumAndCustomerCode(1,6).getAlreadyFine());
    }

    @Test
    @Order(6)
    void payForBillTotally() {
        loanService.payForBillTotally(8000.0,9,1,9);
        Bill bill = loanMapper.getBillFromPeriodNumAndCustomerCode(1,9);
        Assert.assertEquals(0,bill.getRemainingForPay(),0);
        Assert.assertEquals("yes",bill.getAlreadyPay());
        loanService.payForBillTotally(5000.0,7,1,7);
        Bill bill2 = loanMapper.getBillFromPeriodNumAndCustomerCode(1,7);
        Assert.assertEquals(5000.0,bill2.getRemainingForPay(),0);
        Assert.assertEquals("no",bill2.getAlreadyPay());
    }

    @Test
    @Order(5)
    void payForBillPartly() {
        loanService.payForBillPartly(2000.0,9,1,9);
        Bill bill = loanMapper.getBillFromPeriodNumAndCustomerCode(1,9);
        Assert.assertEquals(8000.0,bill.getRemainingForPay(),0);
        Assert.assertEquals("no",bill.getAlreadyPay());
        loanService.payForBillPartly(5000.0,7,1,7);
        Bill bill2 = loanMapper.getBillFromPeriodNumAndCustomerCode(1,7);
        Assert.assertEquals(5000.0,bill2.getRemainingForPay(),0);
        Assert.assertEquals("no",bill2.getAlreadyPay());
    }

}
