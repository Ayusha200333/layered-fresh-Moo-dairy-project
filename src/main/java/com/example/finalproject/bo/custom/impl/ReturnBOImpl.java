package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.ReturnBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.ReturnDAO;
import com.example.finalproject.dto.ReturnDTO;
import com.example.finalproject.entity.ReturnDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnBOImpl implements ReturnBO {
    ReturnDAO returnDAO = (ReturnDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RETURN);

    @Override
    public ArrayList<ReturnDTO> getAll() throws SQLException {
        ArrayList<ReturnDetails> all = returnDAO.getAll();
        ArrayList<ReturnDTO> returnDTOS = new ArrayList<>();
        for (ReturnDetails returnDetails : all) {
            returnDTOS.add(new ReturnDTO(returnDetails.getReturnId(),returnDetails.getReturnDate()));

        }
        return returnDTOS;
    }

    @Override
    public boolean save(ReturnDTO Dto) throws SQLException {
        return returnDAO.save(new ReturnDetails(Dto.getReturnId(),Dto.getReturnDate()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return returnDAO.delete(customerId);
    }

    @Override
    public boolean update(ReturnDTO Dto) throws SQLException {
        return returnDAO.update(new ReturnDetails(Dto.getReturnId(),Dto.getReturnDate()));
    }

    @Override
    public String getNextId() throws SQLException {
        return returnDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return returnDAO.getAllIds();
    }
}
