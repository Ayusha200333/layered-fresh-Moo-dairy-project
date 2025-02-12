package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.OrdersBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.*;
import com.example.finalproject.dto.CustomerDTO;
import com.example.finalproject.dto.OrdersDTO;
import com.example.finalproject.entity.Customer;
import com.example.finalproject.entity.Delivery;
import com.example.finalproject.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersBOImpl implements OrdersBO {
    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERS);

    @Override
    public ArrayList<OrdersDTO> getAll() throws SQLException {
        ArrayList<Orders> all = ordersDAO.getAll();
        ArrayList<OrdersDTO> ordersDTOS = new ArrayList<>();
        for (Orders orders : all) {
            ordersDTOS.add(new OrdersDTO(orders.getOrderId(),orders.getProductId(),orders.getDate(),orders.getAmount(),orders.getCusId(),orders.getPaymentId(),orders.getDeliveryId(),orders.getQty()));

        }
        return ordersDTOS;
    }

    @Override
    public boolean save(OrdersDTO Dto) throws SQLException {
        return ordersDAO.save(new Orders(Dto.getOrderId(),Dto.getProductId(),Dto.getDate(),Dto.getAmount(),Dto.getCusId(),Dto.getPaymentId(),Dto.getDeliveryId(),Dto.getQty()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return ordersDAO.delete(customerId);
    }

    @Override
    public boolean update(OrdersDTO Dto) throws SQLException {
        return ordersDAO.update(new Orders(Dto.getOrderId(),Dto.getProductId(),Dto.getDate(),Dto.getAmount(),Dto.getCusId(),Dto.getPaymentId(),Dto.getDeliveryId(),Dto.getQty()));
    }

    @Override
    public String getNextId() throws SQLException {
        return ordersDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return ordersDAO.getAllIds();
    }
}
