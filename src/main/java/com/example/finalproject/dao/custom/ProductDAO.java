package com.example.finalproject.dao.custom;

import com.example.finalproject.dao.CrudDAO;
import com.example.finalproject.entity.Product;

import java.sql.SQLException;

public interface ProductDAO extends CrudDAO<Product> {
    Product getProduct(String id) throws SQLException;
}
