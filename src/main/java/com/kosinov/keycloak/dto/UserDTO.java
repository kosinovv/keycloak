package com.kosinov.keycloak.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserDTO {

    private String userName; //Код пользователя

    private String longName; //Длинное имя пользователя

    private Date correctDate; //Дата создания/коррекции

}