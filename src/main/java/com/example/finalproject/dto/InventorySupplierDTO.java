package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class InventorySupplierDTO {
    private String invId;
    private String supId;
    private int qty;
}
