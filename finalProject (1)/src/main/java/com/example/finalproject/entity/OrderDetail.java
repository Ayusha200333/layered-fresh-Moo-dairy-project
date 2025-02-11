package com.example.finalproject.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class OrderDetail {
    private String orderId;
    private String proId;
    private int qty;
}
