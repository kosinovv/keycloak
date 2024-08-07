package com.kosinov.keycloak.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String username;
    private String password;
    private String role;
}
