package org.example.api.exception;

import org.example.api.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler(PaginationParamsIsInvalidException.class)
    public ResponseEntity<ErrorDTO> handlePaginationParamsIsInvalidException(
            PaginationParamsIsInvalidException exception, WebRequest request) {
        logger.warn(exception.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorDTO(request.getDescription(false), LocalDateTime.now(), exception.getErrors()));
    }

}
