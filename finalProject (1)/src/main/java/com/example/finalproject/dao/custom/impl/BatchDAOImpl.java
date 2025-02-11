package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.BatchDAO;
import com.example.finalproject.entity.Batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchDAOImpl implements BatchDAO {
    public ArrayList<Batch> getAll () throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Batch");

        ArrayList<Batch> batchDTOs = new ArrayList<>();

        while (rst.next()) {
            Batch batch = new Batch(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
            batchDTOs.add(batch);
        }
        return batchDTOs;
}

    public boolean save (Batch batchDTO) throws SQLException { // less boilerplate code
        return SQLUtil.execute("insert into  Batch values(?,?,? )"
            , batchDTO.getBatchId()
            , batchDTO.getPrice()
            , batchDTO.getProductId());
    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Batch_Id from Batch order by Batch_Id desc limit 1");

        if (rst.next()) {
            String lastBatchId = rst.getString(1);
            String subString = lastBatchId.substring(1);
            int i = Integer.parseInt(subString);
            int newBatchId = i + 1;
            return String.format("B%03d", newBatchId);
        }
        return "B001";
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    public boolean delete (String batchId) throws SQLException {
        return SQLUtil.execute("delete from Batch where Batch_Id=?", batchId);

    } public boolean update (Batch batchDTO) throws SQLException {
        return SQLUtil.execute(
                "update Batch set Price=?,  Pro_Id=? where Batch_Id=?",
                batchDTO.getPrice(),
                batchDTO.getProductId(),
                batchDTO.getBatchId()

        );
    }
}