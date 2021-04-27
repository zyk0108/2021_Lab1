package com.example.test;

import com.example.test.entity.AccountFlow;
import com.example.test.entity.Record;
import com.example.test.service.BuyProduct;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

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
public class BuyProductTest {
    static BuyProduct buyProduct=new BuyProduct();

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
public void testGetAccountGrade() throws Exception {
//TODO: Test goes here...
    Map<String,Object> hashMap;
    hashMap=buyProduct.getAccountGrade("001");
    assertEquals(1,hashMap.get("grade"));
    hashMap=buyProduct.getAccountGrade("004");
    assertEquals(2,hashMap.get("grade"));
    hashMap=buyProduct.getAccountGrade("003");
    assertEquals(3,hashMap.get("grade"));
}

/**
*
* Method: getProductPrice(int productId)
*
*/
@Test
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
public void testCanBuyProduct() throws Exception {
//TODO: Test goes here...
    Map<String,Object>hashMap;
    hashMap=buyProduct.canBuyProduct("001");
    assertEquals(0,hashMap.get("fine"));
    assertEquals(false,hashMap.get("flag"));
    hashMap=buyProduct.canBuyProduct("005");
    assertEquals(9999,hashMap.get("fine"));
    assertEquals(true,hashMap.get("flag"));
}

/**
*
* Method: getAccountBalance(String account)
*
*/
@Test
public void testGetAccountBalance() throws Exception {
//TODO: Test goes here...
    Map<String,Object>hashMap;
    hashMap=buyProduct.getAccountBalance("004");
    assertEquals(10000,hashMap.get("accountBalance"));
}

/**
*
* Method: strToDate(String strDate)
*
*/
@Test
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
public void testBuyProduct() throws Exception {
//TODO: Test goes here...
    int res=buyProduct.buyProduct("001",1,0,"2021.04.24",3,1);
    assertEquals(200,res);
    res=buyProduct.buyProduct("001",1,1000,"2021.04.24",3,1);
    assertEquals(200,res);
}

/**
*
* Method: checkData(String account)
*
*/
@Test
public void testCheckData() throws Exception {
//TODO: Test goes here...
    List<Record> list=buyProduct.checkData("001");
    Record record=list.get(0);
    assertEquals("001",record.getAccount());
    assertEquals("2021-04-11",record.getDate());
}

/**
*
* Method: getAccountFlowInfo(String account)
*
*/
@Test
public void testGetAccountFlowInfo() throws Exception {
//TODO: Test goes here...
    List<AccountFlow>list=buyProduct.getAccountFlowInfo("002");
    AccountFlow accountFlow=list.get(0);
    assertEquals("002",accountFlow.getAccount());
    assertEquals(10000,accountFlow.getAmount());
    assertEquals("2021.04.12",accountFlow.getDate());
}


}
