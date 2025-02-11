package com.example.finalproject.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InventoryTM {
    private String inventoryId;
    private String supId;
    private int qty;

}
