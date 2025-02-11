package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDTO {
    private String customerId;
    private String name;
    private String email;
    private String address;
    private String phoneNo;

}
