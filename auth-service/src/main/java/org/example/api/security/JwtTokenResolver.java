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
import org.example.api.dto.TokenDTO;
import org.example.api.entity.Role;
import org.example.api.exception.JwtAuthenticationException;
import org.example.api.mapper.TokenMapper;
import org.example.api.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtTokenResolver {

    private final String secretKey;
    private final int validityInMinutes;
    private final TokenService tokenService;
    private final TokenMapper tokenMapper;

    public JwtTokenResolver(PasswordEncoder passwordEncoder, JwtProperties jwtProperties, TokenService tokenService,
                            TokenMapper tokenMapper) {
        secretKey = passwordEncoder.encode(jwtProperties.getSecretKey());
        validityInMinutes = jwtProperties.getValidityInMinutes();
        this.tokenService = tokenService;
        this.tokenMapper = tokenMapper;
    }

    public void resolveAuthentication(HttpServletResponse response, String username, Role role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(ApiConstants.ROLE_CLAIMS_FIELD, role);
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(validityInMinutes)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        response.addHeader(ApiConstants.AUTHORIZATION_TOKEN_HEADER_NAME, token);
    }

    /**
     * Authorizes token and throws an exception if role stored at the token is not equal to the {@code role}. Adds
     * old token to the black list and creates new one for the user
     *
     * @param token json web token
     * @param role  role to check
     * @throws JwtAuthenticationException if Role stored at the {@code token} is not equal to the {@code role};
     *                                    if {@code token} is malformed; if {@code token} is invalid; if {@code token
     *                                    } is expired
     */
    public void resolveAuthorization(HttpServletResponse response, String token, Role role)
            throws JwtAuthenticationException {
        TokenDTO tokenDTO = createTokenDTO(token);
        if (!isRoleEqualsToStoredAtToken(role, tokenDTO)) {
            throw new JwtAuthenticationException(ApiConstants.USER_DOES_NOT_HAVE_ACCESS_MESSAGE, HttpStatus.FORBIDDEN);
        }
        tokenService.addToBlacklist(tokenMapper.extractToken(tokenDTO));
        resolveAuthentication(response, tokenDTO.getUsername(), tokenDTO.getRole());
    }

    private TokenDTO createTokenDTO(String token) throws JwtAuthenticationException {
        try {
            Jws<Claims> tokenClaims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if (tokenService.isTokenBlacklisted(token)) {
                throw new JwtAuthenticationException(ApiConstants.AUTH_TOKEN_IS_BLACKLISTED_MESSAGE,
                        HttpStatus.UNAUTHORIZED);
            }

            return new TokenDTO(token, tokenClaims.getBody().getSubject(),
                    Role.valueOf(tokenClaims.getBody().get(ApiConstants.ROLE_CLAIMS_FIELD, String.class)),
                    tokenClaims.getBody().getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        } catch (ExpiredJwtException exception) {
            throw new JwtAuthenticationException(ApiConstants.AUTH_TOKEN_IS_EXPIRED_MESSAGE, HttpStatus.UNAUTHORIZED);
        } catch (SignatureException exception) {
            throw new JwtAuthenticationException(ApiConstants.AUTH_TOKEN_IS_INVALID_MESSAGE, HttpStatus.FORBIDDEN);
        } catch (JwtException | IllegalArgumentException exception) {
            throw new JwtAuthenticationException(ApiConstants.AUTH_TOKEN_IS_MALFORMED_MESSAGE, HttpStatus.FORBIDDEN);
        }
    }

    private boolean isRoleEqualsToStoredAtToken(Role roleToCheck, TokenDTO tokenDTO) {
        return roleToCheck.equals(tokenDTO.getRole());
    }

    public void resolveLogout(HttpServletRequest request) throws JwtAuthenticationException {
        String token = request.getHeader(ApiConstants.AUTHORIZATION_TOKEN_HEADER_NAME);
        if (Objects.isNull(token)) {
            throw new JwtAuthenticationException(ApiConstants.AUTH_TOKEN_IS_MISSING_MESSAGE, HttpStatus.UNAUTHORIZED);
        }
        TokenDTO tokenDTO = createTokenDTO(token);
        tokenService.addToBlacklist(tokenMapper.extractToken(tokenDTO));
    }

}
