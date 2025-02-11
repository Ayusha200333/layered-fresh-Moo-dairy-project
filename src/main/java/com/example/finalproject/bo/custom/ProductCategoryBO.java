package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.ProductCategoryDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductCategoryBO extends SuperBO {
    ArrayList<ProductCategoryDTO> getAll() throws SQLException;
    boolean save (ProductCategoryDTO  Dto)throws SQLException;
    boolean delete (String customerId)throws SQLException;
    boolean update (ProductCategoryDTO Dto)throws SQLException;
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
}
