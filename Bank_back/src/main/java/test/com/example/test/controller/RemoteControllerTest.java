package test.com.example.test.controller;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Map;

/**
* RemoteController Tester.
*
* @author <Authors name>
* @since <pre>ËÄÔÂ 25, 2021</pre>
* @version 1.0
*/
public class RemoteControllerTest {

@Before
public void before() throws Exception {
    System.out.println("Test start.");
}

@After
public void after() throws Exception {
    System.out.println("Test finish.");
}

/**
*
* Method: get(@RequestBody LoginRequest loginRequest)
*
*/
@Test
public void testGet() throws Exception {
//TODO: Test goes here...

}

/**
*
* Method: getAccountGrade(@RequestBody JSONObject jsonObject)
*
*/
@Test
public void testGetAccountGrade() throws Exception {
//TODO: Test goes here...
    JSONObject jsonObject=new JSONObject();
}

/**
*
* Method: getProductPrice(@PathVariable int productId)
*
*/
@Test
public void testGetProductPrice() throws Exception {
//TODO: Test goes here...
}

/**
*
* Method: canBuyProduct(@RequestBody JSONObject jsonObject)
*
*/
@Test
public void testCanBuyProduct() throws Exception {
//TODO: Test goes here...
}

/**
*
* Method: getAccountBalance(@RequestBody JSONObject jsonObject)
*
*/
@Test
public void testGetAccountBalance() throws Exception {
//TODO: Test goes here...
}

/**
*
* Method: strToDate(String strDate)
*
*/
@Test
public void testStrToDate() throws Exception {
//TODO: Test goes here...
}

/**
*
* Method: buyProduct(@RequestBody JSONObject jsonObject)
*
*/
@Test
public void testBuyProduct() throws Exception {
//TODO: Test goes here...
}

/**
*
* Method: checkData(@RequestBody JSONObject jsonObject)
*
*/
@Test
public void testCheckData() throws Exception {
//TODO: Test goes here...
}

/**
*
* Method: getAccountFlowInfo(@RequestBody JSONObject jsonObject)
*
*/
@Test
public void testGetAccountFlowInfo() throws Exception {
//TODO: Test goes here...
}


}
