package com.kosinov.keycloak.dto;

import com.kosinov.keycloak.model.InternalErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private InternalErrorStatus status;

    private String message;
}
