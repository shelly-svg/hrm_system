package org.example.api.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.api.constant.ApiConstants;
import org.example.api.dto.ErrorDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

@Component(ApiConstants.JWT_HANDLER_EXCEPTION_RESOLVER_BEAN)
@RequiredArgsConstructor
public class JwtAuthenticationExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper;

    public void handleJwtTokenIsMissingException(JwtAuthenticationException exception, HttpServletRequest request,
                                                 HttpServletResponse response) throws IOException {
        response.setStatus(exception.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorDTO errorDTO = new ErrorDTO(request.getRequestURI(), LocalDateTime.now(),
                Collections.singletonMap(ApiConstants.ERROR_API_NAME, exception.getMessage()));
        response.getOutputStream().println(objectMapper.writeValueAsString(errorDTO));
        response.getOutputStream().close();
    }

    @SneakyThrows
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception exception) {
        if (exception instanceof JwtAuthenticationException) {
            handleJwtTokenIsMissingException((JwtAuthenticationException) exception, request, response);
        }
        return null;
    }

}
