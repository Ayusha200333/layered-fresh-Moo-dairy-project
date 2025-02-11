package com.example.finalproject.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Payment {
    private String paymentId;
    private String paymentMethod;
    private String paymentDate;

}
