package com.kosinov.keycloak.dto;

import lombok.Data;

@Data
public class PasswordDTO {
    private String userName;
    private String password;
}