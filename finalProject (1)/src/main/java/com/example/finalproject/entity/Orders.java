package com.example.finalproject.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Orders {
    private String orderId;
    private String productId;
    private String date;
    private String amount;
    private String cusId;
    private String paymentId;
    private String deliveryId;
    private int qty;
}

