package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.InventoryDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface InventoryBO extends SuperBO {
    ArrayList<InventoryDTO> getAll() throws SQLException;
    boolean save (InventoryDTO  Dto)throws SQLException;
    boolean delete (String customerId)throws SQLException;
    boolean update (InventoryDTO Dto)throws SQLException;
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
}
