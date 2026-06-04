package com.michaelrichards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {

    private UUID userId;
    private Integer age;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
}
