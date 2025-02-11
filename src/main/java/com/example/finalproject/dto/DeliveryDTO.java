package com.example.finalproject.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeliveryDTO {
    private String deliveryId;
    private String deliveryDate;
    private String destination;

}
