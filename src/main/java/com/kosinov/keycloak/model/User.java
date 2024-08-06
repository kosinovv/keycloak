package com.kosinov.keycloak.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Table(schema="kosinov", name = "users")
@Builder(setterPrefix = "set")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "username")
    private String userName;

    @Column(name = "longname")
    private String longName;

    @Column(name = "correctdate")
    private Date correctDate;

    public User(String userName, String longName, Date correctDate) {
        this.userName = userName;
        this.longName = longName;
        this.correctDate = correctDate;
    }
}