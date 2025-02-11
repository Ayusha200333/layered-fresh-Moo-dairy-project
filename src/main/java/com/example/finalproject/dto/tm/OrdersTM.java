package com.example.finalproject.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrdersTM{
    private String  orderId;
    private String  productId;
    private String date;
    private String amount;
    private String cusId;
    private String paymentId;
    private String deliveryId;
    private int qty;

}
