package org.example.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/gateway")
public class GatewayController {

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("test");
    }

}
