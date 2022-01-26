package org.example.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Getter
public class CreateOpportunityModelIsNotValidException extends Exception {

    private static final long serialVersionUID = 7234932483242342342L;

    private final transient BindingResult bindingResult;

}
