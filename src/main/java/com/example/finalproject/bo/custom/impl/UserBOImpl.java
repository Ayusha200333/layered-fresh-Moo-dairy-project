package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.UserBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.UserDAO;
import com.example.finalproject.dto.UserDTO;
import com.example.finalproject.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public ArrayList<UserDTO> getAll() throws SQLException {
        ArrayList<User> all = userDAO.getAll();
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        for (User user : all) {
            userDTOS.add(new UserDTO(user.getUserId(),user.getPassword(),user.getEmail()));

        }
        return userDTOS;
    }

    @Override
    public boolean save(UserDTO Dto) throws SQLException {
        return userDAO.save(new User(Dto.getUserId(),Dto.getPassword(),Dto.getEmail()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return userDAO.delete(customerId);
    }

    @Override
    public boolean update(UserDTO Dto) throws SQLException {
        return userDAO.update(new User(Dto.getUserId(),Dto.getPassword(),Dto.getEmail()));
    }

    @Override
    public String getNextId() throws SQLException {
        return userDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return userDAO.getAllIds();
    }
}
