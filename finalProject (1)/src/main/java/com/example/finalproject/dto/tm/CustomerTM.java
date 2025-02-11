package com.example.finalproject.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerTM {
    private String customerId;
    private String name;
    private String email;
    private String address;
    private String phoneNo;
}
