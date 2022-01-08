package org.example.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @PostMapping("/api/v1/authenticate")
    String login(@RequestHeader Map<String, String> headers, String loginForm);

}
