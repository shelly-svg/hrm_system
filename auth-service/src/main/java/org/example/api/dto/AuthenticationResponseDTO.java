package org.example.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthenticationResponseDTO {

    private final String username;
    private final String token;

}
