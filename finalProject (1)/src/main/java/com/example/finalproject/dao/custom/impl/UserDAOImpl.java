package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.UserDAO;
import com.example.finalproject.dto.UserDTO;
import com.example.finalproject.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    public ArrayList<User> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from User");
        ArrayList<User> userDTOS = new ArrayList<>();
        while (rst.next()) {
            User userDTO = new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)

            );
            userDTOS.add(userDTO);
        }
        return userDTOS;

    }

    @Override
    public boolean save(User Dto) throws SQLException {
        return false;
    }

    public boolean save (UserDTO userDTO) throws SQLException {
        return SQLUtil.execute("insert into User values(?,?,?)"
        , userDTO.getUserId()
        , userDTO.getPassword()
        , userDTO.getEmail());

    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select User_Id from User order by User_Id desc limit 1");

        if (rst.next()) {
            String lastUserId = rst.getString(1);
            String subString = lastUserId.substring(1);
            int i = Integer.parseInt(subString);
            int newUserId = i+1;
            return String.format("U%03d",newUserId);
        }
        return "U001";
    }
    public boolean delete (String userId) throws SQLException {
        return SQLUtil.execute("delete from User where User_Id=?", userId);

    }

    @Override
    public boolean update(User Dto) throws SQLException {
        return false;
    }

    public boolean update (UserDTO userDTO) throws SQLException {
        return SQLUtil.execute(
                "update User set Password=?, Email=?  where User_Id=?",
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getUserId()

        );
    }


    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

}
