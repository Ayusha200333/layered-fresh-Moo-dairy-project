package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.SupplierBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.SupplierDAO;
import com.example.finalproject.dto.SupplierDTO;
import com.example.finalproject.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public ArrayList<SupplierDTO> getAll() throws SQLException {
        ArrayList<Supplier> all = supplierDAO.getAll();
        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();
        for (Supplier supplier : all) {
            supplierDTOS.add(new SupplierDTO(supplier.getSupplierId(),supplier.getSupplierName(),supplier.getPhoneNO()));

        }
        return supplierDTOS;

    }

    @Override
    public boolean save(SupplierDTO Dto) throws SQLException {
        return supplierDAO.save(new Supplier(Dto.getSupplierId(),Dto.getSupplierName(),Dto.getPhoneNO()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return supplierDAO.delete(customerId);
    }

    @Override
    public boolean update(SupplierDTO Dto) throws SQLException {
        return supplierDAO.update(new Supplier(Dto.getSupplierId(),Dto.getSupplierName(),Dto.getPhoneNO()));
    }

    @Override
    public String getNextId() throws SQLException {
        return supplierDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return supplierDAO.getAllIds();
    }
}
