package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.ReturnDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ReturnBO extends SuperBO {
    ArrayList<ReturnDTO> getAll() throws SQLException;
    boolean save (ReturnDTO  Dto)throws SQLException;
    boolean delete (String customerId)throws SQLException;
    boolean update (ReturnDTO Dto)throws SQLException;
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
}
