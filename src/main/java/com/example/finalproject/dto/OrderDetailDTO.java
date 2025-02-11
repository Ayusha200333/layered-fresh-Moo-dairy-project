package com.example.finalproject.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class OrderDetailDTO {
    private String orderId;
    private String proId;
    private int qty;
}
