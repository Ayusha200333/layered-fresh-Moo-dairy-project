package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private String userId;
    private String password;
    private String  email;
}