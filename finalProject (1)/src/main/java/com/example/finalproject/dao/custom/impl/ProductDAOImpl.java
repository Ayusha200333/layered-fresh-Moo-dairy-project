package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.ProductDAO;
import com.example.finalproject.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductDAOImpl implements ProductDAO {
    public ArrayList<Product> getAll () throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Product");

        ArrayList<Product> productDTOS = new ArrayList<>();

        while (rst.next()) {
            Product productDTO = new Product(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            );
            productDTOS.add(productDTO);
        }
        return productDTOS;

    }

    public boolean save (Product productDTO) throws SQLException {
        return SQLUtil.execute("insert into Product values(?,?,?,?)"
        , productDTO.getProId()
        , productDTO.getProName()
        , productDTO.getPrice()
        , productDTO.getQty());

    }


    public boolean delete (String productId) throws SQLException {
        return SQLUtil.execute("delete from Product where  Pro_Id=?", productId);
    }

    public boolean update (Product productDTO) throws SQLException {
        return SQLUtil.execute(
                "update Product set Pro_Name=?, Price=?, Qty=? where Pro_Id=?",
                productDTO.getProName(),
                productDTO.getPrice(),
                productDTO.getQty(),
                productDTO.getProId()
        );
    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Pro_Id from Product order by Pro_Id desc limit 1");

        if (rst.next()) {
            String lastProductId = rst.getString(1);
            String subString = lastProductId.substring(1);
            int i = Integer.parseInt(subString);
            int newProductId = i+1;
            return String.format("P%03d",newProductId);
        }
        return "P001";
    }

    public boolean update(String proId, int orderQty) throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT Qty FROM Product WHERE Pro_Id = ?", proId);

        if (rs.next()) {
            int availableQty = rs.getInt("Qty");
            if (orderQty <= availableQty){
                return SQLUtil.execute("update Product set Qty=? where Pro_Id=?", (availableQty - orderQty), proId);
            }
            return false;
        } else {
            throw new SQLException("Product not found for Id: " + proId);
        }
    }


    public List<String> getAllIds() throws SQLException {
        List<String> productIds = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT Pro_Id FROM Product");

        while (rs.next()){
            productIds.add(rs.getString(1));
        }
        return productIds;
    }
}























