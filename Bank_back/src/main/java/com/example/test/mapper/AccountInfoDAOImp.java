package com.example.test.mapper;

import com.example.test.entity.AccountInfo;
import com.example.test.mapper.DAO;

public class AccountInfoDAOImp extends DAO<AccountInfo> {
    public AccountInfo getAccountInfo(String accountNum){
        String sql = "select * from accountinfo where accountNum = ?";
        return get(sql,accountNum);
    }
    public void updateDeposit(int deposit, String accountNum){
        String sql = "Update accountinfo set deposit = ? Where accountNum =  ?";
        update(sql,deposit,accountNum);
    }
    public void updateFine(String account){
        String sql4 = "Update accountinfo set fine = ? Where accountNum =  ?";
        update(sql4,0,account);
    }
}
