package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.InventoryBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.BatchDAO;
import com.example.finalproject.dao.custom.InventoryDAO;
import com.example.finalproject.dao.custom.SupplierDAO;
import com.example.finalproject.dto.EmployeeDTO;
import com.example.finalproject.dto.InventoryDTO;
import com.example.finalproject.entity.Employee;
import com.example.finalproject.entity.Inventory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryBOImpl implements InventoryBO {
    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INVENTORY);
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public ArrayList<InventoryDTO> getAll() throws SQLException {
        ArrayList<Inventory> all = inventoryDAO.getAll();
        ArrayList<InventoryDTO> inventoryDTOS = new ArrayList<>();
        for (Inventory inventory : all) {
            inventoryDTOS.add(new InventoryDTO(inventory.getInventoryId(),inventory.getSupId(),inventory.getQty()));

        }
        return inventoryDTOS;
    }

    @Override
    public boolean save(InventoryDTO Dto) throws SQLException {
        return inventoryDAO.save(new Inventory(Dto.getInventoryId(),Dto.getSupId(),Dto.getQty()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return inventoryDAO.delete(customerId);
    }

    @Override
    public boolean update(InventoryDTO Dto) throws SQLException {
        return inventoryDAO.update(new Inventory(Dto.getInventoryId(),Dto.getSupId(),Dto.getQty()));
    }

    @Override
    public String getNextId() throws SQLException {
        return inventoryDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return inventoryDAO.getAllIds();
    }
}
