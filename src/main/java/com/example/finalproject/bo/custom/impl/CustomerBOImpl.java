package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.CustomerBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.CustomerDAO;
import com.example.finalproject.dto.CustomerDTO;
import com.example.finalproject.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);


    @Override
    public boolean delete(String customerId) throws SQLException {
        return customerDAO.delete(customerId);
    }

    @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDtos = new ArrayList<>();
        for (Customer customer : all) {
            customerDtos.add(new CustomerDTO(customer.getCustomerId(),customer.getName(),customer.getEmail(),customer.getAddress(),customer.getPhoneNo()));

        }
        return customerDtos;
    }

    @Override
    public boolean save(CustomerDTO Dto) throws SQLException {
        return customerDAO.save(new Customer(Dto.getCustomerId(),Dto.getName(),Dto.getEmail(),Dto.getAddress(),Dto.getPhoneNo()));
    }

    @Override
    public boolean update(CustomerDTO Dto) throws SQLException {
        return customerDAO.update(new Customer(Dto.getCustomerId(),Dto.getName(),Dto.getEmail(),Dto.getAddress(),Dto.getPhoneNo()));
    }

    @Override
    public String getNextId() throws SQLException {
        return customerDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return customerDAO.getAllIds();
    }
}
