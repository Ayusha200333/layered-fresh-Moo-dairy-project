package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.ProductDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductBO extends SuperBO {
    ArrayList<ProductDTO> getAll() throws SQLException;
    boolean save (ProductDTO  Dto)throws SQLException;
    boolean delete (String customerId)throws SQLException;
    boolean update (ProductDTO Dto)throws SQLException;
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
    ProductDTO getProduct(String id)throws SQLException;
}
