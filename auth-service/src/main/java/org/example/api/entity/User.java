package org.example.api.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password_", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_", nullable = false)
    private Status status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_", nullable = false)
    private Role role;

}