package com.example.finalproject.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class InventorySupplier {
    private String invId;
    private String supId;
    private int qty;
}
