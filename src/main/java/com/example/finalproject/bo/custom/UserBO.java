package com.example.finalproject.bo.custom;

import com.example.finalproject.bo.SuperBO;
import com.example.finalproject.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserBO extends SuperBO {
    ArrayList<UserDTO> getAll() throws SQLException;
    boolean save (UserDTO  Dto)throws SQLException;
    boolean delete (String customerId)throws SQLException;
    boolean update (UserDTO Dto)throws SQLException;
    String getNextId() throws SQLException;
    List<String> getAllIds() throws SQLException;
}
