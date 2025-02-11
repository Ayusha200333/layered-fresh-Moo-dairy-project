package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.DeliveryDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DeliveryBO extends SuperBO {
    ArrayList<DeliveryDTO> getAll() throws SQLException;
    boolean save (DeliveryDTO  Dto)throws SQLException;
    boolean delete (String customerId)throws SQLException;
    boolean update (DeliveryDTO Dto)throws SQLException;
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
}
