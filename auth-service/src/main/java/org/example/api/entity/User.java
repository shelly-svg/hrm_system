package org.example.api.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_")
public class User {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @Column(name = "password_", length = 100, nullable = false)
    @ToString.Exclude
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_", length = 30, nullable = false)
    private Status status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_", length = 30, nullable = false)
    private Role role;

}