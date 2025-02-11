package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.CustomerDTO;
import com.example.finalproject.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerBO extends SuperBO {
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
    boolean delete (String customerId)throws SQLException;
    ArrayList<CustomerDTO> getAll() throws SQLException;
    boolean save (CustomerDTO customerDTO)throws SQLException;
    boolean update (CustomerDTO customerDTO)throws SQLException;

}



