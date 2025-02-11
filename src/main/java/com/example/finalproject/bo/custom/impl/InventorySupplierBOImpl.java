package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.InventorySupplierBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.BatchDAO;
import com.example.finalproject.dao.custom.InventorySupplierDAO;
import com.example.finalproject.dto.InventoryDTO;
import com.example.finalproject.dto.InventorySupplierDTO;
import com.example.finalproject.entity.Inventory;
import com.example.finalproject.entity.InventorySupplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventorySupplierBOImpl implements InventorySupplierBO {
    InventorySupplierDAO inventorySupplierDAO = (InventorySupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INVENTORYSUPPLIER);

    @Override
    public ArrayList<InventorySupplierDTO> getAll() throws SQLException {
        ArrayList<InventorySupplier> all = inventorySupplierDAO.getAll();
        ArrayList<InventorySupplierDTO> inventorySupplierDTOS = new ArrayList<>();
        for (InventorySupplier inventorySupplier : all) {
            inventorySupplierDTOS.add(new InventorySupplierDTO(inventorySupplier.getInvId(),inventorySupplier.getSupId(),inventorySupplier.getQty()));

        }
        return inventorySupplierDTOS;
    }

    @Override
    public boolean save(InventorySupplierDTO Dto) throws SQLException {
        return inventorySupplierDAO.save(new InventorySupplier(Dto.getInvId(),Dto.getSupId(),Dto.getQty()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return inventorySupplierDAO.delete(customerId);
    }

    @Override
    public boolean update(InventorySupplierDTO Dto) throws SQLException {
        return inventorySupplierDAO.update(new InventorySupplier(Dto.getInvId(),Dto.getSupId(),Dto.getQty()));
    }

    @Override
    public String getNextId() throws SQLException {
        return inventorySupplierDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return inventorySupplierDAO.getAllIds();
    }
}
