package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.constant.ApiConstants;
import org.example.api.dto.OpportunityDTO;
import org.example.api.entity.Opportunity;
import org.example.api.entity.Status;
import org.example.api.exception.PositionNotFoundException;
import org.example.api.service.FileService;
import org.example.api.service.OpportunityService;
import org.example.api.service.PositionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/opportunities")
@RequiredArgsConstructor
public class OpportunityController {

    private final OpportunityService opportunityService;
    private final PositionService positionService;
    private final FileService fileService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Opportunity> getById(@PathVariable(ApiConstants.ID_PATH_PARAM) long opportunityId) {
        Optional<Opportunity> requestedOpportunity = opportunityService.getById(opportunityId);
        return requestedOpportunity.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Opportunity> create(@ModelAttribute OpportunityDTO opportunityDTO)
            throws PositionNotFoundException {
        return new ResponseEntity<>(opportunityService.create(createOpportunity(opportunityDTO)), HttpStatus.CREATED);
    }

    private Opportunity createOpportunity(OpportunityDTO opportunityDTO) throws PositionNotFoundException {
        Opportunity opportunityToCreate = new Opportunity();
        opportunityToCreate.setPosition(positionService.getById(opportunityDTO.getPositionId())
                .orElseThrow(() -> new PositionNotFoundException(
                        ApiConstants.CANNOT_FIND_POSITION_MESSAGE + opportunityDTO.getPositionId())));

        // candidate id check must be implemented here (get request to 'user-service/users')
        opportunityToCreate.setCandidateId(opportunityDTO.getCandidateId());

        opportunityToCreate.setStatus(Status.NEW);
        opportunityToCreate.setCvFileName(fileService.saveCv(opportunityDTO.getCv(), opportunityDTO.getPositionId(),
                opportunityDTO.getCandidateId()));
        opportunityToCreate.setCoverLetterFileName(
                fileService.saveCoverLetter(opportunityDTO.getCoverLetter(), opportunityDTO.getPositionId(),
                        opportunityDTO.getCandidateId()));
        return opportunityToCreate;
    }

}