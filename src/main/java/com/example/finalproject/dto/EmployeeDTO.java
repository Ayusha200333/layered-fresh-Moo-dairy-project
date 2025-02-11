package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmployeeDTO {
    private String employeeId;
    private String employeeName;
    private int employeeSalary;
    private String employeePhoneNo;

}
