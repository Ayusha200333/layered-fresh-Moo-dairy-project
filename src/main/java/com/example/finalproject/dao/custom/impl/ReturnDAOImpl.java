package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.ReturnDAO;
import com.example.finalproject.entity.ReturnDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnDAOImpl implements ReturnDAO {
    public ArrayList<ReturnDetails> getAll () throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Return_detail");

        ArrayList<ReturnDetails> returnDTOS = new ArrayList<>();

        while (rst.next()) {
            ReturnDetails returnDTO = new ReturnDetails(
                    rst.getString(1),
                    rst.getString(2)
            );
            returnDTOS.add(returnDTO);
        }
        return returnDTOS;

    }

    public boolean save (ReturnDetails returnDTO) throws SQLException {
        return SQLUtil.execute("insert into Return_detail values(?,?)"
        , returnDTO.getReturnId()
        , returnDTO.getReturnDate());

    }


    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select return_id from Return_detail order by return_id desc limit 1");

        if (rst.next()) {
            String lastReturnId = rst.getString(1);
            String subString = lastReturnId.substring(1);
            int i = Integer.parseInt(subString);
            int newReturnId = i+1;
            return String.format("R%03d",newReturnId);
        }
        return "R001";
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    public boolean delete (String returnId) throws SQLException {
        return SQLUtil.execute("delete from Return_detail where return_id=?", returnId);

    }

    public boolean update (ReturnDetails returnDTO) throws SQLException {
        return SQLUtil.execute(
                "update Return_detail set retu_date=? where return_id=?",
                returnDTO.getReturnDate(),
                returnDTO.getReturnId()
        );
    }
}
