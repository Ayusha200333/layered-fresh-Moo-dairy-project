package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.DeliveryBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.BatchDAO;
import com.example.finalproject.dao.custom.DeliveryDAO;
import com.example.finalproject.dto.CustomerDTO;
import com.example.finalproject.dto.DeliveryDTO;
import com.example.finalproject.entity.Customer;
import com.example.finalproject.entity.Delivery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryBOImpl implements DeliveryBO {
    DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DELIVERY);

    @Override
    public ArrayList<DeliveryDTO> getAll() throws SQLException {
        ArrayList<Delivery> all = deliveryDAO.getAll();
        ArrayList<DeliveryDTO> deliveryDTOS = new ArrayList<>();
        for (Delivery delivery : all) {
            deliveryDTOS.add(new DeliveryDTO(delivery.getDeliveryId(),delivery.getDeliveryDate(),delivery.getDestination()));

        }
        return deliveryDTOS;
    }

    @Override
    public boolean save(DeliveryDTO Dto) throws SQLException {
        return deliveryDAO.save(new Delivery(Dto.getDeliveryId(),Dto.getDeliveryDate(),Dto.getDestination()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return deliveryDAO.delete(customerId);
    }

    @Override
    public boolean update(DeliveryDTO Dto) throws SQLException {
        return deliveryDAO.update(new Delivery(Dto.getDeliveryId(),Dto.getDeliveryDate(),Dto.getDestination()));
    }

    @Override
    public String getNextId() throws SQLException {
        return deliveryDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return deliveryDAO.getAllIds();
    }
}
