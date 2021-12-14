package org.example.api.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class AuthenticationDTO {

    private String username;

    @ToString.Exclude
    private String password;

}
