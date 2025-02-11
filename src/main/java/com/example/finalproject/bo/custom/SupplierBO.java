package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SupplierBO extends SuperBO {
    ArrayList<SupplierDTO> getAll() throws SQLException;
    boolean save (SupplierDTO  Dto)throws SQLException;
    boolean delete (String customerId)throws SQLException;
    boolean update (SupplierDTO Dto)throws SQLException;
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
}
