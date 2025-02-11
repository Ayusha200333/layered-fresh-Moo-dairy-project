package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dto.SignupDTO;

import java.sql.SQLException;

public class SignupDAOImpl {
    public boolean saveSignup(SignupDTO signupDTO) throws SQLException {

        return SQLUtil.execute("insert into Admin values(?,?,?,?)"
        , signupDTO.getUsername()
        , signupDTO.getPassword()
        , signupDTO.getConfirmPassword()
        , signupDTO.getEmail());

    }
}
