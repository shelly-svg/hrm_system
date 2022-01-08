package org.example.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @PostMapping("/api/v1/authentication")
    String login(String loginForm);

}
