package org.example.api.exception;

import org.example.api.constant.ApiConstants;
import org.example.api.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDTO> handleAuthenticationException(BadCredentialsException exception, WebRequest req) {
        logger.debug(exception.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(req.getDescription(false), LocalDateTime.now(),
                Collections.singletonMap(ApiConstants.ERROR_API_NAME, ApiConstants.INCORRECT_USERNAME_OR_PWD_MESSAGE));
        return new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorDTO> handleLockedException(LockedException exception, WebRequest req) {
        logger.debug(exception.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(req.getDescription(false), LocalDateTime.now(),
                Collections.singletonMap(ApiConstants.ERROR_API_NAME, ApiConstants.USER_IS_BLOCKED_MESSAGE));
        return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorDTO> handleJwtAuthException(JwtAuthenticationException exception, WebRequest req) {
        logger.debug(exception.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(req.getDescription(false), LocalDateTime.now(),
                Collections.singletonMap(ApiConstants.ERROR_API_NAME, exception.getMessage()));
        return new ResponseEntity<>(errorDTO, exception.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        logger.debug(exception.getMessage());
        Map<String, String> validationErrors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest()
                .body(new ErrorDTO(request.getDescription(false), LocalDateTime.now(), validationErrors));
    }

}
