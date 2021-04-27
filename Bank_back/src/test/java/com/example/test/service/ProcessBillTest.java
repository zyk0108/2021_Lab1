package com.example.test.service;

import com.example.test.entity.Bill;
import com.example.test.mapper.LoanMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProcessBillTest {

    @Autowired private LoanMapper loanMapper;
    @Autowired private LoanService loanService;

    @Test
    public void dailyProcess() {
        loanService.dailyProcess();
        Bill bill = loanMapper.getBillFromPeriodNumAndCustomerCode(1,6);
        Assert.assertEquals(0,bill.getRemainingForPay(),0);
        Assert.assertEquals("yes",bill.getAlreadyPay());
        Bill bill2 = loanMapper.getBillFromPeriodNumAndCustomerCode(1,8);
        Assert.assertEquals("no",bill2.getAlreadyFine());
        Bill bill3 = loanMapper.getBillFromPeriodNumAndCustomerCode(1,7);
        Assert.assertEquals("yes",bill3.getAlreadyFine());
        Assert.assertEquals("no",bill3.getAlreadyPay());
    }
}
