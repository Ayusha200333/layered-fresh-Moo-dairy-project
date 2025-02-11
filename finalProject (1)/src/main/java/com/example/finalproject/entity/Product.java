package com.example.finalproject.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private String proId;
    private String proName;
    private double price;
    private int qty;

}
