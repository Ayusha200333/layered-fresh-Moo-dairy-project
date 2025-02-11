package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.InventoryDAO;
import com.example.finalproject.entity.Inventory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAOImpl implements InventoryDAO {
    public ArrayList<Inventory> getAll () throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Inventory");
        ArrayList<Inventory> inventoryDTOS = new ArrayList<>();
        while (rst.next()) {
            Inventory inventoryDTO = new Inventory(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)

            );
            inventoryDTOS.add(inventoryDTO);
        }
        return inventoryDTOS;
    }

    public boolean save (Inventory inventoryDTO) throws SQLException {
        return SQLUtil.execute("insert into Inventory values(?,?,?)"
        , inventoryDTO.getInventoryId()
        , inventoryDTO.getSupId()
        , inventoryDTO.getQty());

    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Inv_Id from Inventory order by Inv_Id desc limit 1");

        if (rst.next()) {
            String lastInventoryId = rst.getString(1);
            String subString = lastInventoryId.substring(1);
            int i = Integer.parseInt(subString);
            int newInventoryId = i+1;
            return String.format("I%03d",newInventoryId);
        }
        return "I001";
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    public boolean delete (String inventoryId) throws SQLException {
        return SQLUtil.execute("delete from Inventory where Inv_Id=?", inventoryId);

    }

    public boolean update (Inventory inventoryDTO) throws SQLException {
        return SQLUtil.execute(
                "update Inventory set  Sup_Id=?, Qty=? where  Inv_Id=?",
                inventoryDTO.getSupId(),
                inventoryDTO.getQty(),
                inventoryDTO.getInventoryId()

        );
    }

}
