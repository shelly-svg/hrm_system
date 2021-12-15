package org.example.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.example.api.constant.ApiConstants;
import org.example.api.constant.JwtProperties;
import org.example.api.entity.Role;
import org.example.api.exception.JwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtTokenResolver {

    private final String secretKey;
    private final int validityInMinutes;

    public JwtTokenResolver(PasswordEncoder passwordEncoder, JwtProperties jwtProperties) {
        secretKey = passwordEncoder.encode(jwtProperties.getSecretKey());
        validityInMinutes = jwtProperties.getValidityInMinutes();
    }

    public String createToken(String username, Role role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(ApiConstants.ROLE_CLAIMS_FIELD, role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(validityInMinutes)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public void authorize(String token, Role role) throws JwtAuthenticationException {
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
