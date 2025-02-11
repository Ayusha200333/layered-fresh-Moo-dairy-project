package com.example.finalproject.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Inventory {
    private String inventoryId;
    private String supId;
    private int qty;

}