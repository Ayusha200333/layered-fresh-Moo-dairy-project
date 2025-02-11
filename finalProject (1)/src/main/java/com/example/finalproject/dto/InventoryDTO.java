package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InventoryDTO {
    private String inventoryId;
    private String supId;
    private int qty;

}