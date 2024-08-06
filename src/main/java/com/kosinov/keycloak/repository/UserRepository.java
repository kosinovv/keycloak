package com.kosinov.keycloak.repository;

import com.kosinov.keycloak.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String tabNum);

}