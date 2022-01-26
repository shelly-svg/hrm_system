package org.example.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class PaginationParamsIsInvalidException extends Exception {

    private static final long serialVersionUID = -924783247348432324L;

    private final Map<String, String> errors;

}
