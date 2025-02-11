package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.InventorySupplierDAO;
import com.example.finalproject.entity.InventorySupplier;
import com.example.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventorySupplierDAOImpl implements InventorySupplierDAO {

    @Override
    public ArrayList<InventorySupplier> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from inventory_supplier");
        ArrayList<InventorySupplier> inventorySuppliers = new ArrayList<>();

        while (rst.next()) {
            InventorySupplier inventoryDTO = new InventorySupplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)

            );
            inventorySuppliers.add(inventoryDTO);
        }
        return inventorySuppliers;
    }

    public boolean save (InventorySupplier inventorySupplierDTO) throws SQLException {

        return SQLUtil.execute("insert into Inventory_Supplier values(?,?,?)"
        , inventorySupplierDTO.getInvId()
        , inventorySupplierDTO.getSupId()
        , inventorySupplierDTO.getQty());
    }

    public boolean update (InventorySupplier inventorySupplierDTO) throws SQLException {
        return CrudUtil.execute(
                "update Inventory_Supplier set  Sup_Id=?, Qty=? where  Inv_Id=?",
                inventorySupplierDTO.getSupId(),
                inventorySupplierDTO.getQty(),
                inventorySupplierDTO.getInvId()
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


    public boolean delete (String inventoryId) throws SQLException {
        return CrudUtil.execute("delete from Inventory_Supplier where Inv_Id=?", inventoryId);
    }
}
