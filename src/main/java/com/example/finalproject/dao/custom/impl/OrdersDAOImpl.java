package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.OrdersDAO;
import com.example.finalproject.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAOImpl implements OrdersDAO {
    public boolean save (Orders ordersDTO) throws SQLException {
        return SQLUtil.execute("insert into Orders values(?,?,?,?,?,?,?,?)"
        , ordersDTO.getOrderId()
        , ordersDTO.getProductId()
        , ordersDTO.getDate()
        , ordersDTO.getAmount()
        , ordersDTO.getCusId()
        , ordersDTO.getPaymentId()
        , ordersDTO.getDeliveryId()
        , ordersDTO.getQty());


    }


    public ArrayList<Orders> getAll () throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Orders");

        ArrayList<Orders> ordersDTOs = new ArrayList<>();

        while (rst.next()) {
            Orders orders = new Orders(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getInt(8)
            );
            ordersDTOs.add(orders);
        }
        return ordersDTOs;
    }

    public boolean update (Orders ordersDTO) throws SQLException {
        return SQLUtil.execute(
                "update Orders set  Pro_Id=? ,Ord_date=?,  Amount=?, Customer_Id=?, Payment_Id=?, Deli_Id=?, Qty=? where Order_Id=?",
                ordersDTO.getProductId(),
                ordersDTO.getDate(),
                ordersDTO.getAmount(),
                ordersDTO.getCusId(),
                ordersDTO.getPaymentId(),
                ordersDTO.getDeliveryId(),
                ordersDTO.getQty(),
                ordersDTO.getOrderId()
        );
    }
    public boolean delete (String orderId) throws SQLException {
        return SQLUtil.execute("delete from Orders Where Order_Id=?", orderId);
    }

    public String getNextId() throws SQLException {

        ResultSet rst = SQLUtil.execute( "select Order_Id from Orders order by Order_id desc limit 1");

        if (rst.next()) {
            String lastOrderId = rst.getString(1);
            String subString = lastOrderId.substring(1);
            int i = Integer.parseInt(subString);
            int nextOrderId = i + 1;
            return String.format("O%03d",nextOrderId);
        }
        return "O001";
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }
}
