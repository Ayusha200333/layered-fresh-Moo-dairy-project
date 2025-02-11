package com.example.finalproject.bo.custom.impl;

import com.example.finalproject.bo.custom.EmployeeBO;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.custom.BatchDAO;
import com.example.finalproject.dao.custom.EmployeeDAO;
import com.example.finalproject.dto.DeliveryDTO;
import com.example.finalproject.dto.EmployeeDTO;
import com.example.finalproject.entity.Delivery;
import com.example.finalproject.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public ArrayList<EmployeeDTO> getAll() throws SQLException {
        ArrayList<Employee> all = employeeDAO.getAll();
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee : all) {
            employeeDTOS.add(new EmployeeDTO(employee.getEmployeeId(),employee.getEmployeeName(),employee.getEmployeeSalary(),employee.getEmployeePhoneNo()));

        }
        return employeeDTOS;
    }

    @Override
    public boolean save(EmployeeDTO Dto) throws SQLException {
        return employeeDAO.save(new Employee(Dto.getEmployeeId(),Dto.getEmployeeName(),Dto.getEmployeeSalary(),Dto.getEmployeePhoneNo()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return employeeDAO.delete(customerId);
    }

    @Override
    public boolean update(EmployeeDTO Dto) throws SQLException {
        return employeeDAO.update(new Employee(Dto.getEmployeeId(),Dto.getEmployeeName(),Dto.getEmployeeSalary(),Dto.getEmployeePhoneNo()));
    }

    @Override
    public String getNextId() throws SQLException {
        return employeeDAO.getNextId();
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return employeeDAO.getAllIds();
    }
}
