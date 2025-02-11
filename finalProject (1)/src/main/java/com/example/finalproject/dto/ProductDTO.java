package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {
    private String proId;
    private String proName;
    private double price;
    private int qty;

}
