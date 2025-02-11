package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.ProductCategoryBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.BatchDAO;
import com.example.finalproject.dao.custom.ProductCategoryDAO;
import com.example.finalproject.dto.ProductCategoryDTO;
import com.example.finalproject.dto.ProductDTO;
import com.example.finalproject.entity.Product;
import com.example.finalproject.entity.ProductCategory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryBOImpl implements ProductCategoryBO {
    ProductCategoryDAO productCategoryDAO = (ProductCategoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCTCATEGORY);

    @Override
    public ArrayList<ProductCategoryDTO> getAll() throws SQLException {
        ArrayList<ProductCategory> all = productCategoryDAO.getAll();
        ArrayList<ProductCategoryDTO> productCategoryDTOS = new ArrayList<>();
        for (ProductCategory productCategory : all) {
            productCategoryDTOS.add(new ProductCategoryDTO(productCategory.getCategoryId(),productCategory.getCategoryName()));

        }
        return productCategoryDTOS;
    }

    @Override
    public boolean save(ProductCategoryDTO Dto) throws SQLException {
        return productCategoryDAO.save(new ProductCategory(Dto.getCategoryId(),Dto.getCategoryName()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return productCategoryDAO.delete(customerId);
    }

    @Override
    public boolean update(ProductCategoryDTO Dto) throws SQLException {
        return productCategoryDAO.update(new ProductCategory(Dto.getCategoryId(),Dto.getCategoryName()));
    }

    @Override
    public String getNextId() throws SQLException {
        return productCategoryDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return productCategoryDAO.getAllIds();
    }
}
