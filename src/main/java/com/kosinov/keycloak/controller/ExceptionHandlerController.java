package com.kosinov.keycloak.controller;

import com.kosinov.keycloak.dto.ErrorResponse;
import com.kosinov.keycloak.exception.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.kosinov.keycloak.model.InternalErrorStatus.USER_NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFound(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(USER_NOT_FOUND, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
