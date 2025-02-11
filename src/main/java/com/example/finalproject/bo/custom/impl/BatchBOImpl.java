package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.BatchBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.BatchDAO;
import com.example.finalproject.dto.BatchDTO;
import com.example.finalproject.entity.Batch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchBOImpl implements BatchBO {
    BatchDAO batchDAO = (BatchDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BATCH);

    @Override
    public ArrayList<BatchDTO> getAll() throws SQLException {

        ArrayList<Batch> all = batchDAO.getAll();
        ArrayList<BatchDTO> batchDTOS = new ArrayList<>();
        for (Batch batch : all) {
            batchDTOS.add(new BatchDTO(batch.getBatchId(),batch.getPrice(),batch.getProductId()));

        }
        return batchDTOS;

    }

    @Override
    public boolean save(BatchDTO Dto) throws SQLException {
       return batchDAO.save(new Batch(Dto.getBatchId(),Dto.getPrice(),Dto.getProductId()));

    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return batchDAO.delete(customerId);
    }

    @Override
    public boolean update(BatchDTO Dto) throws SQLException {
        return batchDAO.update(new Batch(Dto.getBatchId(),Dto.getPrice(),Dto.getProductId()));
    }

    @Override
    public String getNextId() throws SQLException {
        return batchDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return batchDAO.getAllIds();
    }
}
