package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.InventoryDTO;
import com.example.finalproject.dto.InventorySupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface InventorySupplierBO extends SuperBO {
    ArrayList<InventorySupplierDTO> getAll() throws SQLException;
    boolean save (InventorySupplierDTO  Dto)throws SQLException;
    boolean delete (String customerId)throws SQLException;
    boolean update (InventorySupplierDTO Dto)throws SQLException;
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
}
