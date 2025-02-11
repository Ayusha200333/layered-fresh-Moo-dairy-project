package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dto.LoginDTO;
import com.example.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImpl {
    public boolean SaveLogin(LoginDTO loginDTO) throws SQLException {
//        List<String> login = new ArrayList<>();

        ResultSet rs = CrudUtil.execute("SELECT * FROM Admin WHERE Email = ? AND Password = ?",
                loginDTO.getEmail(),
                loginDTO.getPassword());

        if (rs.next()) {
            return true; // Login successful
        } else {
            return false; // Invalid credentials
        }
    }

}
