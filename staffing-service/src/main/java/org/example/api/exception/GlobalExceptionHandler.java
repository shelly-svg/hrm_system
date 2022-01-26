package org.example.api.exception;

import org.example.api.constant.ApiConstants;
import org.example.api.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        logger.debug(exception.getMessage());
        Map<String, String> validationErrors = getValidationErrorsFromBindingResult(exception.getBindingResult());
        return ResponseEntity.badRequest()
                .body(new ErrorDTO(request.getDescription(false), LocalDateTime.now(), validationErrors));
    }

    private Map<String, String> getValidationErrorsFromBindingResult(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(PaginationParamsIsInvalidException.class)
    public ResponseEntity<ErrorDTO> handlePaginationParamsIsInvalidException(
            PaginationParamsIsInvalidException exception, WebRequest request) {
        logger.warn(exception.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorDTO(request.getDescription(false), LocalDateTime.now(), exception.getErrors()));
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProjectNotFoundException(ProjectNotFoundException exception,
                                                                   WebRequest request) {
        logger.warn(exception.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorDTO(request.getDescription(false), LocalDateTime.now(),
                        Collections.singletonMap(ApiConstants.PROJECT_ID_FORM_PARAM, exception.getMessage())));
    }

    @ExceptionHandler(CreateOpportunityModelIsNotValidException.class)
    public ResponseEntity<ErrorDTO> handleCreateOpportunityModelIsNotValidException(
            CreateOpportunityModelIsNotValidException exception, WebRequest request) {
        Map<String, String> validationErrors = getValidationErrorsFromBindingResult(exception.getBindingResult());
        return ResponseEntity.badRequest()
                .body(new ErrorDTO(request.getDescription(false), LocalDateTime.now(), validationErrors));
    }

}
