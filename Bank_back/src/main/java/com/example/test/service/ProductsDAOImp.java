package com.example.test.service;

import com.example.test.entity.Products;
import com.example.test.mapper.DAO;

public class ProductsDAOImp extends DAO<Products> {
    public Products getProducts(int type){
        String sql = "select price from products where type = ?";
        return get(sql,type);
    }
}
