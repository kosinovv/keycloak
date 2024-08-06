package com.kosinov.keycloak.mapper;

import com.kosinov.keycloak.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUserName(resultSet.getString("username"));
        user.setLongName(resultSet.getString("longname"));
        user.setCorrectDate(resultSet.getDate("correctdate"));
        return user;
    }
}

