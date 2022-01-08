package org.example.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.example.api.client.AuthClient;
import org.example.api.dto.AuthDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/gateway")
@RequiredArgsConstructor
public class GatewayController {

    private final AuthClient authClient;
    private final ObjectMapper objectMapper;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> login() throws JsonProcessingException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        Response response = authClient.login(headers, objectMapper.writeValueAsString(new AuthDTO("admin", "admin")));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", response.headers().get("Authorization").stream().findFirst().orElseThrow());
        return ResponseEntity.status(response.status()).headers(httpHeaders).build();
    }

}
