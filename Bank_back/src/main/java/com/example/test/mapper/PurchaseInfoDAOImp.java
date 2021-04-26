package com.example.test.mapper;

import com.example.test.entity.PurchaseInfo;

import java.sql.Date;
import java.util.List;

public class PurchaseInfoDAOImp extends DAO<PurchaseInfo> {
    public void addPurchaseInfo(String account, int productID, Date date, int amount, int duration){
        String sql3 = "insert into purchase_info (account, productType, time, amount, duration) values(?,?,?,?,?)";
        update(sql3,account,productID,date,amount,duration);
    }

    public List<PurchaseInfo> getInfos(String account){
        String sql = "select * from purchase_info where account = ?";
        return getAll(sql,account);
    }
}
