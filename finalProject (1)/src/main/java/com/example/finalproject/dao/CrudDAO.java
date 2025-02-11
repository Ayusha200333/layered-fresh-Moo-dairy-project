package com.example.finalproject.dao;

import com.example.finalproject.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO <T>{ //generics//
    ArrayList<T> getAll() throws SQLException;

    boolean save (T  Dto)throws SQLException;

    boolean delete (String customerId)throws SQLException;

    boolean update (T Dto)throws SQLException;
    String getNextId() throws SQLException;

    List<String> getAllIds() throws SQLException;
}
