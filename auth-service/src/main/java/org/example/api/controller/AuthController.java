package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.dto.AuthenticationDTO;
import org.example.api.dto.AuthenticationResponseDTO;
import org.example.api.entity.User;
import org.example.api.repository.UserRepository;
import org.example.api.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationDTO authenticationDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                authenticationDTO.getPassword()));
        User user = userRepository.getUserByUsername(authenticationDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("user does not exist"));
        String token = jwtTokenProvider.createToken(authenticationDTO.getUsername(), user.getRole().name());
        return ResponseEntity.ok(new AuthenticationResponseDTO(authenticationDTO.getUsername(), token));
    }

}
