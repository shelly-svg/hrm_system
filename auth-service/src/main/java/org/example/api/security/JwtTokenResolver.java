package org.example.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.example.api.constant.ApiConstants;
import org.example.api.constant.JwtProperties;
import org.example.api.entity.Role;
import org.example.api.exception.JwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtTokenResolver {

    private final JwtProperties jwtProperties;
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(ApiConstants.ROLE_CLAIMS_FIELD, role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(jwtProperties.getValidityInMinutes())))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public void authorize(String token, Role role) throws JwtAuthenticationException {
        if (Objects.isNull(token)) {
            throw new JwtAuthenticationException(ApiConstants.AUTHORIZATION_TOKEN_IS_MISSING_MESSAGE,
                    HttpStatus.UNAUTHORIZED);
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (!role.name().equals(claimsJws.getBody().get(ApiConstants.ROLE_CLAIMS_FIELD, String.class))) {
                throw new JwtAuthenticationException(ApiConstants.USER_DOES_NOT_HAVE_ACCESS_MESSAGE,
                        HttpStatus.FORBIDDEN);
            }
        } catch (ExpiredJwtException exception) {
            throw new JwtAuthenticationException(ApiConstants.AUTH_TOKEN_IS_EXPIRED_MESSAGE, HttpStatus.UNAUTHORIZED);
        } catch (SignatureException exception) {
            throw new JwtAuthenticationException(ApiConstants.AUTH_TOKEN_IS_INVALID_MESSAGE, HttpStatus.FORBIDDEN);
        } catch (JwtException | IllegalArgumentException exception) {
            throw new JwtAuthenticationException(ApiConstants.AUTH_TOKEN_IS_MALFORMED_MESSAGE, HttpStatus.FORBIDDEN);
        }
    }

}
