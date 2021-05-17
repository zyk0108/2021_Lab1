package com.example.test.mapper;

import com.example.test.entity.Transaction;

import java.sql.Date;
import java.util.List;

public class TransactionDAOImp extends DAO<Transaction> {
    public void addTransaction(String account, Date date, String operation, int amount){
        String sql2 = "insert into transaction (account, time, operation,amount) values( ?,?,?,?)";
        update(sql2,account,date,operation,amount);
    }

    public List<Transaction> getTransactions(String account){
        String sql = "select * from transaction where account = ?";
        return getAll(sql,account);
    }
}
