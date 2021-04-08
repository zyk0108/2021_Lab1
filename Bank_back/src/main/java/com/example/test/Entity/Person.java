package com.example.test.Entity;

import lombok.Data;

@Data
public class Person {
    //@JsonProperty("token")
    String name;
    //@JsonProperty("expireTime")
    int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
