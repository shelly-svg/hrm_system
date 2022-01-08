package org.example.api.controller;

import org.example.api.client.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/gateway")
public class GatewayController {

    @Autowired
    private AuthClient authClient;

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String json = "{\n" + "    \"username\":\"admin\",\n" + "    \"password\":\"admin\"\n" + "}";
        HttpEntity<String> request = new HttpEntity<>(json, httpHeaders);
        return ResponseEntity.ok(authClient.login(json));
    }

}
