package org.example.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ErrorDTO {

    private final String endpoint;
    private final LocalDateTime time;
    private final Map<String, String> userInfo;

}
