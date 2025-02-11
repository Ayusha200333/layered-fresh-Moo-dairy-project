package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.OrderDetailDTO;
import com.example.finalproject.dto.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrdersBO extends SuperBO {
    ArrayList<OrdersDTO> getAll() throws SQLException;
    boolean save (OrdersDTO Dto)throws SQLException;
    boolean delete (String customerId)throws SQLException;
    boolean update (OrdersDTO Dto)throws SQLException;
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
}
