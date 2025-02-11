package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.OrderDetailBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.BatchDAO;
import com.example.finalproject.dao.custom.OrderDetailDAO;
import com.example.finalproject.dto.InventoryDTO;
import com.example.finalproject.dto.OrderDetailDTO;
import com.example.finalproject.entity.Inventory;
import com.example.finalproject.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailBOImpl implements OrderDetailBO {
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERDETAIL);

    @Override
    public ArrayList<OrderDetailDTO> getAll() throws SQLException {
        ArrayList<OrderDetail> all = orderDetailDAO.getAll();
        ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
        for (OrderDetail orderDetail : all) {
            orderDetailDTOS.add(new OrderDetailDTO(orderDetail.getOrderId(),orderDetail.getProId(),orderDetail.getQty()));

        }
        return orderDetailDTOS;
    }

    @Override
    public boolean save(OrderDetailDTO Dto) throws SQLException {
        return orderDetailDAO.save(new OrderDetail(Dto.getOrderId(),Dto.getProId(),Dto.getQty()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return orderDetailDAO.delete(customerId);
    }

    @Override
    public boolean update(OrderDetailDTO Dto) throws SQLException {
        return orderDetailDAO.update(new OrderDetail(Dto.getOrderId(),Dto.getProId(),Dto.getQty()));
    }

    @Override
    public String getNextId() throws SQLException {
        return orderDetailDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return orderDetailDAO.getAllIds();
    }
}
