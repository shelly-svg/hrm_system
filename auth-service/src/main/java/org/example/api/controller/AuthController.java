package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.constant.ApiConstants;
import org.example.api.dto.AuthenticationDTO;
import org.example.api.dto.AuthorizationDTO;
import org.example.api.entity.User;
import org.example.api.exception.JwtAuthenticationException;
import org.example.api.security.JwtTokenResolver;
import org.example.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenResolver jwtTokenResolver;

    @PostMapping("/authenticate")
    public ResponseEntity<HttpStatus> authenticate(@RequestBody @Valid AuthenticationDTO authDTO,
                                                   HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
        User user = userService.getUserByUsername(authDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(ApiConstants.USER_DOES_NOT_EXIST_MESSAGE));
        jwtTokenResolver.resolveToken(response, authDTO.getUsername(), user.getRole());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/authorize")
    public ResponseEntity<HttpStatus> authorize(@RequestBody @Valid AuthorizationDTO authorizationDTO)
            throws JwtAuthenticationException {
        jwtTokenResolver.authorize(authorizationDTO.getToken(), authorizationDTO.getRole());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
