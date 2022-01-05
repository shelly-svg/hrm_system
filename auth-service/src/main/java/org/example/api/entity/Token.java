package org.example.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token_blacklist")
public class Token {

    @Id
    @Column(name = "token", length = 250, unique = true, nullable = false)
    private String token;

    @Column(name = "expiration", nullable = false)
    private LocalDateTime expiration;

}
