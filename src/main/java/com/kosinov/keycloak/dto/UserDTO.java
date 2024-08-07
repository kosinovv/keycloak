package com.kosinov.keycloak.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}