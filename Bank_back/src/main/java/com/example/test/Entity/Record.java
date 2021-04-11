package com.example.test.Entity;

import lombok.Data;

@Data
public class Record {
    private String account;
    private String theProduct;
    private int condition;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTheProduct() {
        return theProduct;
    }

    public void setTheProduct(String theProduct) {
        this.theProduct = theProduct;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }
}
