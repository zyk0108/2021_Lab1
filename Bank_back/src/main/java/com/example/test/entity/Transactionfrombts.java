package com.example.test.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Transactionfrombts {
    private Integer id;
    private Integer operator;
    private Timestamp operatorTime;
    private String transactionCode;
    private String branchNum;
    private String transactionNum;
    private String account;
    private String reciprocalAccount;
    private String reciprocalName;
    private Integer amount;
    private Integer currency;
    private Integer balance;
    private String tellerName;
    private String branchName;
    private String transactionType;
    private String password;
    private Integer depositId;
    private String idNumber;
    private Integer verifyType1;

    @Basic
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "operator")
    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    @Basic
    @Column(name = "operatorTime")
    public Timestamp getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Timestamp operatorTime) {
        this.operatorTime = operatorTime;
    }

    @Basic
    @Column(name = "transactionCode")
    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    @Basic
    @Column(name = "branchNum")
    public String getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(String branchNum) {
        this.branchNum = branchNum;
    }

    @Basic
    @Column(name = "transactionNum")
    public String getTransactionNum() {
        return transactionNum;
    }

    public void setTransactionNum(String transactionNum) {
        this.transactionNum = transactionNum;
    }

    @Basic
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "reciprocalAccount")
    public String getReciprocalAccount() {
        return reciprocalAccount;
    }

    public void setReciprocalAccount(String reciprocalAccount) {
        this.reciprocalAccount = reciprocalAccount;
    }

    @Basic
    @Column(name = "reciprocalName")
    public String getReciprocalName() {
        return reciprocalName;
    }

    public void setReciprocalName(String reciprocalName) {
        this.reciprocalName = reciprocalName;
    }

    @Basic
    @Column(name = "amount")
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "currency")
    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "balance")
    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "tellerName")
    public String getTellerName() {
        return tellerName;
    }

    public void setTellerName(String tellerName) {
        this.tellerName = tellerName;
    }

    @Basic
    @Column(name = "branchName")
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Basic
    @Column(name = "transactionType")
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "depositId")
    public Integer getDepositId() {
        return depositId;
    }

    public void setDepositId(Integer depositId) {
        this.depositId = depositId;
    }

    @Basic
    @Column(name = "idNumber")
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Basic
    @Column(name = "verifyType1")
    public Integer getVerifyType1() {
        return verifyType1;
    }

    public void setVerifyType1(Integer verifyType1) {
        this.verifyType1 = verifyType1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transactionfrombts that = (Transactionfrombts) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(operatorTime, that.operatorTime) &&
                Objects.equals(transactionCode, that.transactionCode) &&
                Objects.equals(branchNum, that.branchNum) &&
                Objects.equals(transactionNum, that.transactionNum) &&
                Objects.equals(account, that.account) &&
                Objects.equals(reciprocalAccount, that.reciprocalAccount) &&
                Objects.equals(reciprocalName, that.reciprocalName) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(tellerName, that.tellerName) &&
                Objects.equals(branchName, that.branchName) &&
                Objects.equals(transactionType, that.transactionType) &&
                Objects.equals(password, that.password) &&
                Objects.equals(depositId, that.depositId) &&
                Objects.equals(idNumber, that.idNumber) &&
                Objects.equals(verifyType1, that.verifyType1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operator, operatorTime, transactionCode, branchNum, transactionNum, account, reciprocalAccount, reciprocalName, amount, currency, balance, tellerName, branchName, transactionType, password, depositId, idNumber, verifyType1);
    }
}
