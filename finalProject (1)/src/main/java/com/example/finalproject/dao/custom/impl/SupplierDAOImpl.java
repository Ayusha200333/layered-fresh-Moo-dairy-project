package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.SupplierDAO;
import com.example.finalproject.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    public ArrayList<Supplier> getAll () throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Supplier");
        ArrayList<Supplier> supplierDTOS = new ArrayList<>();
        while (rst.next()) {
            Supplier supplierDTO = new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
            supplierDTOS.add(supplierDTO);
        }
        return supplierDTOS;

    }
    public boolean save (Supplier supplierDTO) throws SQLException {

        return SQLUtil.execute("insert into Supplier values(?,?,?)"
        , supplierDTO.getSupplierId()
        , supplierDTO.getSupplierName()
        , supplierDTO.getPhoneNO());

    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Sup_Id from Supplier order by Sup_Id desc limit 1");

        if (rst.next()) {
            String lastSupplierId = rst.getString(1);
            String subString = lastSupplierId.substring(1);
            int i = Integer.parseInt(subString);
            int newSupplierId = i+1;
            return String.format("S%03d",newSupplierId);
        }
        return "S001";
    }
    public boolean delete (String supplierId) throws SQLException {
        return SQLUtil.execute("delete from Supplier where Sup_Id=?", supplierId);

    }
    public boolean update(Supplier supplierDTO) throws SQLException {
        return SQLUtil.execute(
                "update Supplier set Sup_Name=?, PhoneNO=? where Sup_Id=?",

                supplierDTO.getSupplierName(),
                supplierDTO.getPhoneNO(),
                supplierDTO.getSupplierId()

        );
    }

    public List<String> getAllIds() throws SQLException {
        List<String> supplierIds = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT Sup_Id FROM Supplier");

        while (rs.next()){
            supplierIds.add(rs.getString(1));
        }
        return supplierIds;
    }
}
