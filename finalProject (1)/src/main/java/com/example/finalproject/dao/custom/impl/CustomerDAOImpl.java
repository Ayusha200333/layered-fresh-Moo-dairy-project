package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.CustomerDAO;
import com.example.finalproject.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    public ArrayList<Customer> getAll() throws SQLException {
         ResultSet rst = SQLUtil.execute("select * from Customer");

         ArrayList<Customer> customerDTOS = new ArrayList<>();

         while (rst.next()) {
             Customer customer = new Customer(
                     rst.getString(1),
                     rst.getString(2),
                     rst.getString(3),
                     rst.getString(4),
                     rst.getString(5)

             );
             customerDTOS.add(customer);
         }
         return customerDTOS;

    }

    public boolean save(Customer customerDTO) throws SQLException { //less boilerplate code
        return SQLUtil.execute("insert into Customer values(?,?,?,?,?)"
      , customerDTO.getCustomerId()
      , customerDTO.getName()
      , customerDTO.getEmail()
      , customerDTO.getAddress()
      , customerDTO.getPhoneNo());

    }


  public boolean delete (String customerId) throws SQLException {
      return SQLUtil.execute("delete from Customer where customer_id=?", customerId);

  }

    public boolean update (Customer customerDTO) throws SQLException {
        return SQLUtil.execute(
                "update Customer set Name=?, Email=?, Address=?,PhoneNo=? where Customer_Id=?",
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getAddress(),
                customerDTO.getPhoneNo(),
                customerDTO.getCustomerId()
        );
    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute( "select Customer_Id from Customer order by Customer_Id desc limit 1");


        if (rst.next()) {
            String lastCustomerId = rst.getString(1);//C001
            String subString = lastCustomerId.substring(1); //001
            int i = Integer.parseInt(subString); //1
            int newCustomerId = i+1; //2
            return String.format("C%03d", newCustomerId);
        }
        return "C001";
    }

    public List<String> getAllIds() throws SQLException {
        List<String> customerIds = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT Customer_Id FROM Customer");

        while (rs.next()){
            customerIds.add(rs.getString(1));
        }
        return customerIds;
    }
}
