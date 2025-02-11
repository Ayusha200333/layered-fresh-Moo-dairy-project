package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.DeliveryDAO;
import com.example.finalproject.entity.Delivery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAOImpl implements DeliveryDAO {
    public ArrayList<Delivery> getAll () throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Delivery");
        ArrayList<Delivery> deliveryDTOS = new ArrayList<>();
        while (rst.next()) {
            Delivery deliveryDTO = new Delivery(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)

            );
            deliveryDTOS.add(deliveryDTO);
        }
        return deliveryDTOS;

    }
    public boolean save (Delivery deliveryDTO) throws SQLException {
        return SQLUtil.execute("insert into Delivery values(?,?,?)"
        , deliveryDTO.getDeliveryId()
        , deliveryDTO.getDeliveryDate()
        , deliveryDTO.getDestination());

    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Deli_Id from Delivery order by Deli_Id desc limit 1");

        if (rst.next()) {
            String lastDeliveryId = rst.getString(1);
            String subString = lastDeliveryId.substring(1);
            int i = Integer.parseInt(subString);
            int newDeliveryId = i+1;
            return String.format("D%03d",newDeliveryId);
        }
        return "D001";
    }

    public boolean delete (String deliveryId) throws SQLException {
        return SQLUtil.execute("delete from Delivery where Deli_Id=?", deliveryId);

    }

    public boolean update (Delivery deliveryDTO) throws SQLException {
        return SQLUtil.execute(
                "update Delivery set Deli_date=?, Destination=? where Deli_Id=?",
                deliveryDTO.getDeliveryDate(),
                deliveryDTO.getDestination(),
                deliveryDTO.getDeliveryId()
        );
    }

//    @Override
//    public String getNextId() throws SQLException {
//        return "";
//    }

    public List<String> getAllIds() throws SQLException {
        List<String> deliveryIds = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT Deli_Id FROM Delivery");

        while (rs.next()){
            deliveryIds.add(rs.getString(1));
        }
        return deliveryIds;
    }
}
