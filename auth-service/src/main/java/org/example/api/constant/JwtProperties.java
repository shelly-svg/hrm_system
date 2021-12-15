package org.example.api.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class JwtProperties {

    private final String secretKey;
    private final long validityInMinutes;

}
