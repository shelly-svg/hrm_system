package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.constant.ApiConstants;
import org.example.api.dto.CreateOpportunityDTO;
import org.example.api.entity.Opportunity;
import org.example.api.exception.CreateOpportunityModelIsNotValidException;
import org.example.api.exception.PositionNotFoundException;
import org.example.api.exception.UserNotFoundException;
import org.example.api.service.FileService;
import org.example.api.service.OpportunityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/opportunities")
@RequiredArgsConstructor
public class OpportunityController {

    private final OpportunityService opportunityService;
    private final FileService fileService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Opportunity> getById(@PathVariable(ApiConstants.ID_PATH_PARAM) long opportunityId) {
        Optional<Opportunity> requestedOpportunity = opportunityService.getById(opportunityId);
        return requestedOpportunity.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@ModelAttribute @Valid CreateOpportunityDTO createOpportunityDTO,
                                         BindingResult bindingResult)
            throws PositionNotFoundException, UserNotFoundException, CreateOpportunityModelIsNotValidException {
        if (bindingResult.hasErrors()) {
            throw new CreateOpportunityModelIsNotValidException(bindingResult);
        }
        return new ResponseEntity<>(opportunityService.create(createOpportunityDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/cv")
    public ResponseEntity<HttpStatus> isCvPresent(@PathVariable(ApiConstants.ID_PATH_PARAM) long opportunityId) {
        if (fileService.isCvPresent(opportunityId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/cover-letter")
    public ResponseEntity<HttpStatus> isCoverLetterPresent(
            @PathVariable(ApiConstants.ID_PATH_PARAM) long opportunityId) {
        if (fileService.isCoverLetterPresent(opportunityId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}