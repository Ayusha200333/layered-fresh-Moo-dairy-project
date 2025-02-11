package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    ArrayList<EmployeeDTO> getAll() throws SQLException;
    boolean save (EmployeeDTO  Dto)throws SQLException;
    boolean delete (String customerId)throws SQLException;
    boolean update (EmployeeDTO Dto)throws SQLException;
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
}
