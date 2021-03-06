package com.example.test;

import com.example.test.entity.AccountFlow;
import com.example.test.entity.Bill;
import com.example.test.entity.Record;
import com.example.test.mapper.LoanMapper;
import com.example.test.service.BuyProduct;
import com.example.test.service.LoanService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.testng.AssertJUnit.assertEquals;

/**
 * BuyProduct Tester.
 *
 * @author <Authors name>
 * @since <pre>April 26, 2021</pre>
 * @version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BuyProductTest {
    @Autowired private BuyProduct buyProduct;
    @Autowired private LoanService loanService;
    @Autowired private LoanMapper loanMapper;

    @Before
    public void before() throws Exception {
        System.out.println("Test start");
    }

    @After
    public void after() throws Exception {
        System.out.println("Test finish");
    }

    /**
     *
     * Method: getAccountGrade(String account)
     *
     */
    @Test
    @Order(1)
    public void testGetAccountGrade() throws Exception {
//TODO: Test goes here...
        Map<String,Object> hashMap;
        hashMap=buyProduct.getAccountGrade("1");
        assertEquals(1,hashMap.get("grade"));
        hashMap=buyProduct.getAccountGrade("4");
        assertEquals(2,hashMap.get("grade"));
        hashMap=buyProduct.getAccountGrade("3");
        assertEquals(3,hashMap.get("grade"));
    }

    /**
     *
     * Method: getProductPrice(int productId)
     *
     */
    @Test
    @Order(2)
    public void testGetProductPrice() throws Exception {
//TODO: Test goes here...
        Map<String,Object>hashMap;
        hashMap=buyProduct.getProductPrice(1);
        assertEquals(1000,hashMap.get("productPrice"));
        hashMap=buyProduct.getProductPrice(2);
        assertEquals(2000,hashMap.get("productPrice"));
        hashMap=buyProduct.getProductPrice(3);
        assertEquals(5000,hashMap.get("productPrice"));
    }

    /**
     *
     * Method: canBuyProduct(String account)
     *
     */
    @Test
    @Order(3)
    public void testCanBuyProduct() throws Exception {
//TODO: Test goes here...
        Map<String,Object>hashMap;
        hashMap=buyProduct.canBuyProduct("1");
        assertEquals(0,hashMap.get("fine"));
        assertEquals(false,hashMap.get("flag"));
        hashMap=buyProduct.canBuyProduct("5");
        assertEquals(9999,hashMap.get("fine"));
        assertEquals(true,hashMap.get("flag"));
    }

    /**
     *
     * Method: getAccountBalance(String account)
     *
     */
    @Test
    @Order(4)
    public void testGetAccountBalance() throws Exception {
//TODO: Test goes here...
        Map<String,Object>hashMap;
        hashMap=buyProduct.getAccountBalance("4");
        assertEquals(10000,hashMap.get("accountBalance"));
    }

    /**
     *
     * Method: strToDate(String strDate)
     *
     */
    @Test
    @Order(5)
    public void testStrToDate() throws Exception {
//TODO: Test goes here...
        Date date=buyProduct.strToDate("2021.04.25");
        java.sql.Date theDate = new java.sql.Date(new SimpleDateFormat("yyyy.MM.dd").parse("2021.04.25").getTime());
        assertEquals(date,theDate);
    }

    /**
     *
     * Method: buyProduct(String account, int productID, int fine, String theDate, int duration, int productNum)
     *
     */
    @Test
    @Order(6)
    public void testBuyProduct() throws Exception {
//TODO: Test goes here...
        int res=buyProduct.buyProduct("1",1,0,"2021.04.24",3,1);
        assertEquals(200,res);
        //res=buyProduct.buyProduct("1",1,1000,"2021.04.24",3,1);
        //assertEquals(200,res);

        int res2 = buyProduct.buyProduct("0",1,250,"2021.04.24",3,1);
        assertEquals(200,res2);
    }

    /**
     *
     * Method: checkData(String account)
     *
     */
    @Test
    @Order(7)
    public void testCheckData() throws Exception {
//TODO: Test goes here...
        List<Record> list=buyProduct.checkData("1");
        Record record=list.get(0);
        assertEquals("1",record.getAccount());
        assertEquals("2021-04-11",record.getDate());
    }

    /**
     *
     * Method: getAccountFlowInfo(String account)
     *
     */
    @Test
    @Order(8)
    public void testGetAccountFlowInfo() throws Exception {
//TODO: Test goes here...
        List<AccountFlow>list=buyProduct.getAccountFlowInfo("2");
        AccountFlow accountFlow=list.get(0);
        assertEquals("2",accountFlow.getAccount());
        assertEquals(10000,accountFlow.getAmount());
        assertEquals("2021.04.12",accountFlow.getDate());
    }


}

