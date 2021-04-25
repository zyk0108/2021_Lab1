package com.example.test.mapper;

import com.example.test.entity.AccountInfo;
import com.example.test.entity.Bill;
import com.example.test.entity.LoanAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LoanMapper {

    @Select("select * from loan_account")
    List<LoanAccount> getAllLoanAccount();

    @Select("select * from loan_account where customerCode = #{customerCode}")
    List<LoanAccount> getLoanAccountFromCustomerCode(@Param("customerCode")int code);

    @Select("select * from loan_account where customerCode = #{customerCode} and loan_num = #{loan_num}")
    LoanAccount getLoanAccountFromCustomerCodeAndLoanNum(@Param("customerCode")int customerCode,
                                                         @Param("loan_num")int loan_num);

    @Select("select * from loan_account where customerId = #{customerId}")
    List<LoanAccount> getLoanAccountFromCustomerId(@Param("customerId")int id);

    @Select("select * from bill")
    List<Bill> getAllBill();

    @Select("select * from bill where customerCode = #{customerCode}")
    List<Bill> getBillFromCustomerCode(@Param("customerCode")int code);

    @Select("select * from bill where customerCode = #{customerCode} and alreadyPay <> 'yes'")
    List<Bill> getNoPayBill(@Param("customerCode")int code);

    @Select("select * from bill where periodNum = #{periodNum} and customerCode = #{customerCode}")
    Bill getBillFromPeriodNumAndCustomerCode(@Param("periodNum")int periodNum, @Param("customerCode")int code);

    @Select("select customerId from loan_account where customerCode = #{customerCode}")
    int getCustomerIdFromCustomerCode(@Param("customerCode")int customerCode);

    @Select("select remainAmount from card where customerId = #{customerId}")
    double getRemainAmountById(@Param("customerId")int customerId);

    @Select("select * from accountinfo where accountNum = #{accountNum}")
    AccountInfo getAccountInfoFromCustomerCode(@Param("accountNum")String accountNum);

    @Update("update loan_account set fine = #{fine} where customerCode = #{customerCode} and " +
            "loan_num = #{loan_num}")
    void updateFineForLoanAccount(@Param("fine")double fine, @Param("customerCode")int customerCode,
                                  @Param("loan_num")int loan_num);

    @Update("update bill set fine = #{fine}, alreadyFine = 'yes' where periodNum = #{periodNum} and customerCode = #{customerCode}")
    void updateFineForBill(@Param("fine")double fine, @Param("periodNum")int periodNum,
                           @Param("customerCode")int customerCode);

    @Update("update bill set fine = #{fine}, alreadyFine = #{alreadyFine}, remainingForPay = #{remainingForPay}, " +
            "alreadyPay = #{alreadyPay} where periodNum = #{periodNum} and customerCode = #{customerCode}")
    void updateBill(Bill bill);

    @Update("update card set remainAmount = #{remainAmount} where customerId = #{customerId}")
    void updateCard(@Param("remainAmount")double remainAmount, @Param("customerId")int customerId);

    @Update("update accountinfo set fine = #{fine} where accountNum = #{accountNum}")
    void updateFineForAccountInfo(@Param("fine")int fine, @Param("accountNum")String accountNum);

    @Update("update accountinfo set fine = #{fine}, loan = #{loan}, deposit = #{deposit}")
    void updateAccountInfo(AccountInfo accountInfo);

}
