package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.OrderDetailDAO;
import com.example.finalproject.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException {
        return null;
    }

    public boolean save (OrderDetail orderDetailDTO) throws SQLException {
        return SQLUtil.execute("insert into Order_detail values(?,?,?)"
        , orderDetailDTO.getOrderId()
        , orderDetailDTO.getProId()
        , orderDetailDTO.getQty());

    }

    public boolean update (OrderDetail orderDetailDTO) throws SQLException {
        return SQLUtil.execute(
                "update Order_detail set Pro_Id=?, Qty=? where Order_Id=?",
                orderDetailDTO.getProId(),
                orderDetailDTO.getQty(),
                orderDetailDTO.getOrderId()
        );
    }

    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    public boolean delete (String orderId) throws SQLException {
        return SQLUtil.execute("delete from Order_detail where  order_Id=?", orderId);
    }
}
