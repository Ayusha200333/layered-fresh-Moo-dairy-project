package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PaymentDTO {
    private String paymentId;
    private String paymentMethod;
    private String paymentDate;

}
