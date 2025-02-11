package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.PaymentBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.BatchDAO;
import com.example.finalproject.dao.custom.PaymentDAO;
import com.example.finalproject.dto.EmployeeDTO;
import com.example.finalproject.dto.PaymentDTO;
import com.example.finalproject.entity.Employee;
import com.example.finalproject.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public ArrayList<PaymentDTO> getAll() throws SQLException {
        ArrayList<Payment> all = paymentDAO.getAll();
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment payment : all) {
            paymentDTOS.add(new PaymentDTO(payment.getPaymentId(),payment.getPaymentMethod(),payment.getPaymentDate()));

        }
        return paymentDTOS;
    }

    @Override
    public boolean save(PaymentDTO Dto) throws SQLException {
        return paymentDAO.save(new Payment(Dto.getPaymentId(),Dto.getPaymentMethod(),Dto.getPaymentDate()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return paymentDAO.delete(customerId);
    }

    @Override
    public boolean update(PaymentDTO Dto) throws SQLException {
        return paymentDAO.update(new Payment(Dto.getPaymentId(),Dto.getPaymentMethod(),Dto.getPaymentDate()));
    }

    @Override
    public String getNextId() throws SQLException {
        return paymentDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return paymentDAO.getAllIds();
    }
}
