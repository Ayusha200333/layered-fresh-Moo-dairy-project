package com.example.finalproject.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String address;
    private String phoneNo;

}
