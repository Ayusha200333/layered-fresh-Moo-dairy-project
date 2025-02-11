package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentBO extends SuperBO {
    ArrayList<PaymentDTO> getAll() throws SQLException;
    boolean save (PaymentDTO  Dto)throws SQLException;
    boolean delete (String customerId)throws SQLException;
    boolean update (PaymentDTO Dto)throws SQLException;
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
}
