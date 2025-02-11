package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SignupDTO {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
}
