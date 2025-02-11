package com.example.finalproject.dao.custom.impl;

import com.example.finalproject.dao.SQLUtil;
import com.example.finalproject.dao.custom.EmployeeDAO;
import com.example.finalproject.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    public ArrayList<Employee> getAll () throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Employee");
        ArrayList<Employee> employeeDTOS = new ArrayList<>();
        while (rst.next()) {
            Employee employeeDTO = new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4)

            );
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;

    }

    public boolean save (Employee employeeDTO) throws SQLException {
        return SQLUtil.execute("insert into Employee values(?,?,?,?)"
        , employeeDTO.getEmployeeId()
        , employeeDTO.getEmployeeName()
        , employeeDTO.getEmployeeSalary()
        , employeeDTO.getEmployeePhoneNo());

    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Emp_Id from Employee order by Emp_Id desc limit 1");

        if (rst.next()) {
            String lastEmployeeId = rst.getString(1);
            String subString = lastEmployeeId.substring(1);
            int i = Integer.parseInt(subString);
            int newEmployeeId = i+1;
            return String.format("E%03d",newEmployeeId);
        }
        return "E001";
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return List.of();
    }

    public boolean delete (String employeeId) throws SQLException {
        return SQLUtil.execute("delete from Employee where Emp_Id=?", employeeId);

    }

    public boolean update (Employee employeeDTO) throws SQLException {
        return SQLUtil.execute(
                "update Employee set Emp_name =?, Salary=?, PhoneNo=? where Emp_Id=?",

                employeeDTO.getEmployeeName(),
                employeeDTO.getEmployeeSalary(),
                employeeDTO.getEmployeePhoneNo(),
                employeeDTO.getEmployeeId()
        );
    }
}

