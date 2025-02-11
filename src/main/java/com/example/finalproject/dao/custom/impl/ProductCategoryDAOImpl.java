package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.ProductCategoryDAO;
import com.example.finalproject.entity.ProductCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDAOImpl implements ProductCategoryDAO {
    public ArrayList<ProductCategory> getAll () throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Product_Category");

        ArrayList<ProductCategory> productCategoryDTOS = new ArrayList<>();

        while (rst.next()) {
            ProductCategory productCategoryDTO = new ProductCategory(
                    rst.getString(1),
                    rst.getString(2)

            );
            productCategoryDTOS.add(productCategoryDTO);
        }
        return productCategoryDTOS;

    }

    public boolean save (ProductCategory productCategoryDTO) throws SQLException {
        return SQLUtil.execute("insert into Product_Category values(?,?)"
        , productCategoryDTO.getCategoryId()
        , productCategoryDTO.getCategoryName());

    }


    public boolean delete (String categoryId) throws SQLException {
        return SQLUtil.execute("delete from Product_Category where Cat_Id=?", categoryId);

    }

    public boolean update (ProductCategory productCategoryDTO) throws SQLException {
        return SQLUtil.execute(
                "update Product_Category set Cat_Name=? where Cat_Id=?",

                productCategoryDTO.getCategoryName(),
                productCategoryDTO.getCategoryId()
        );
    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Cat_Id from Product_Category order by Cat_Id desc limit 1");

        if (rst.next()) {
            String lastCategoryId = rst.getString(1);
            String subString = lastCategoryId.substring(1);
            int i = Integer.parseInt(subString);
            int newCategoryId = i+1;
            return String.format("C%03d",newCategoryId);
        }
        return "C001";
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

}
