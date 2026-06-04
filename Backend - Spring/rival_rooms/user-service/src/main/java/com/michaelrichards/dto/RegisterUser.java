package com.michaelrichards.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;


@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private LocalDate birthday;
    private String password;
    private String confirmPassword;
}
