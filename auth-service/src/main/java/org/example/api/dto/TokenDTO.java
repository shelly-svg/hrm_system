package org.example.api.dto;

import lombok.Data;
import org.example.api.entity.Role;

import java.time.LocalDateTime;

@Data
public class TokenDTO {

    private final String token;
    private final String username;
    private final Role role;
    private final LocalDateTime expiration;

}
