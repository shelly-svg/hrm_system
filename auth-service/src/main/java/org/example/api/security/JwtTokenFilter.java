package org.example.api.security;

import lombok.extern.slf4j.Slf4j;
import org.example.api.constant.ApiConstants;
import org.example.api.exception.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String AUTH_ENDPOINT = "/api/v1/auth";

    private final JwtTokenProvider jwtTokenProvider;
    private final HandlerExceptionResolver exceptionResolver;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider,
                          @Qualifier(ApiConstants.JWT_HANDLER_EXCEPTION_RESOLVER_BEAN) HandlerExceptionResolver exceptionResolver) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);

        if (Objects.isNull(token)) {
            exceptionResolver.resolveException(request, response, null,
                    new JwtAuthenticationException(ApiConstants.AUTHORIZATION_TOKEN_IS_MISSING_MESSAGE,
                            HttpStatus.UNAUTHORIZED));
            return;
        }

        try {
            if (jwtTokenProvider.isValid(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (Objects.nonNull(authentication)) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException exception) {
            log.debug(exception.getMessage());
            SecurityContextHolder.clearContext();
            exceptionResolver.resolveException(request, response, null, exception);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return AUTH_ENDPOINT.equals(request.getRequestURI());
    }

}
