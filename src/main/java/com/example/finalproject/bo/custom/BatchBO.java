package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.BatchDTO;
import com.example.finalproject.entity.Batch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface BatchBO extends SuperBO {
    ArrayList<BatchDTO> getAll() throws SQLException;

    boolean save (BatchDTO  Dto)throws SQLException;

    boolean delete (String customerId)throws SQLException;

    boolean update (BatchDTO Dto)throws SQLException;
    String getNextId() throws SQLException;

    List<String> getAllIds() throws SQLException;
}
