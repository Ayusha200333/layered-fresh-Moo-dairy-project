package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.ProductBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.BatchDAO;
import com.example.finalproject.dao.custom.ProductDAO;
import com.example.finalproject.dto.PaymentDTO;
import com.example.finalproject.dto.ProductDTO;
import com.example.finalproject.entity.Payment;
import com.example.finalproject.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBOImpl implements ProductBO {
    ProductDAO productDAO = (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT);

    @Override
    public ArrayList<ProductDTO> getAll() throws SQLException {
        ArrayList<Product> all = productDAO.getAll();
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : all) {
            productDTOS.add(new ProductDTO(product.getProId(),product.getProName(),product.getPrice(),product.getQty()));

        }
        return productDTOS;
    }

    @Override
    public boolean save(ProductDTO Dto) throws SQLException {
        return productDAO.save(new Product(Dto.getProId(),Dto.getProName(),Dto.getPrice(),Dto.getQty()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return productDAO.delete(customerId);
    }

    @Override
    public boolean update(ProductDTO Dto) throws SQLException {
        return productDAO.update(new Product(Dto.getProId(),Dto.getProName(),Dto.getPrice(),Dto.getQty()));
    }

    @Override
    public String getNextId() throws SQLException {
        return productDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return productDAO.getAllIds();
    }

    @Override
    public ProductDTO getProduct(String id) throws SQLException {
        Product product = productDAO.getProduct(id);
        return (product != null )? new ProductDTO(product.getProId(),product.getProName(),product.getPrice(),product.getQty()) : null;
    }
}
