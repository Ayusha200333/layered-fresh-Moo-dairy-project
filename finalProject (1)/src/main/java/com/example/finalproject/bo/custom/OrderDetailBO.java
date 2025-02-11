package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDetailBO extends SuperBO {
    ArrayList<OrderDetailDTO> getAll() throws SQLException;
    boolean save (OrderDetailDTO Dto)throws SQLException;
    boolean delete (String customerId)throws SQLException;
    boolean update (OrderDetailDTO Dto)throws SQLException;
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
}
