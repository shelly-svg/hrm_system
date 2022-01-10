package org.example.api.controller;

import feign.Response;
import lombok.RequiredArgsConstructor;
import org.example.api.client.AuthClient;
import org.example.api.constant.ApiConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gateway/v1")
@RequiredArgsConstructor
public class GatewayController {

    private final AuthClient authClient;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> login(HttpServletRequest request) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Response response = authClient.login(headers,
                request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
        HttpHeaders httpHeaders = new HttpHeaders();
        if (response.status() == HttpStatus.OK.value()) {
            httpHeaders.set(ApiConstants.AUTH_TOKEN_HEADER_NAME,
                    response.headers().get(ApiConstants.AUTH_TOKEN_HEADER_NAME).stream().findFirst().orElseThrow());
        }
        return ResponseEntity.status(response.status()).headers(httpHeaders).build();
    }

}
