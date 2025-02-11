package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.PaymentDAO;
import com.example.finalproject.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PaymentDAOImpl implements PaymentDAO {
    public ArrayList<Payment> getAll () throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Payment");
        ArrayList<Payment> paymentDTOS = new ArrayList<>();
        while (rst.next()) {
            Payment paymentDTO = new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)


            );
            paymentDTOS.add(paymentDTO);
        }
        return paymentDTOS;

    }

    public boolean save (Payment paymentDTO) throws SQLException {

        return SQLUtil.execute("insert into Payment values(?,?,?)"
        , paymentDTO.getPaymentId()
        , paymentDTO.getPaymentMethod()
        , paymentDTO.getPaymentDate());

    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Pay_Id from Payment order by Pay_Id desc limit 1");

        if (rst.next()) {
            String lastPaymentId = rst.getString(1);
            String subString = lastPaymentId.substring(1);
            int i = Integer.parseInt(subString);
            int newPaymentId = i + 1;
            return String.format("P%03d", newPaymentId);
        }
        return "P001";
    }

    public boolean delete (String paymentId) throws SQLException {
        return SQLUtil.execute("delete from Payment where Pay_Id=?", paymentId);

    }

    public boolean update (Payment paymentDTO) throws SQLException {
        return SQLUtil.execute(
                "update Payment set Pay_method=?, Pay_date=? where Pay_Id=?",
                paymentDTO.getPaymentMethod(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getPaymentId()

        );
    }

    public List<String> getAllIds() throws SQLException {
        List<String> paymentIds = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT Pay_Id FROM Payment");

        while (rs.next()){
            paymentIds.add(rs.getString(1));
        }
        return paymentIds;
    }
}




