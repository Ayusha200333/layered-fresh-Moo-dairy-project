package com.example.finalproject.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Employee {
    private String employeeId;
    private String employeeName;
    private int employeeSalary;
    private String employeePhoneNo;

}
