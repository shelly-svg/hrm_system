package org.example.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.constant.ApiConstants;
import org.example.api.dto.CreateOpportunityDTO;
import org.example.api.entity.Opportunity;
import org.example.api.entity.Status;
import org.example.api.exception.PositionNotFoundException;
import org.example.api.exception.UserNotFoundException;
import org.example.api.repository.OpportunityRepository;
import org.example.api.repository.PositionRepository;
import org.example.api.service.FileService;
import org.example.api.service.OpportunityService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpportunityServiceImpl implements OpportunityService {

    private final FileService fileService;
    private final OpportunityRepository opportunityRepository;
    private final PositionRepository positionRepository;

    @Override
    public Optional<Opportunity> getById(long opportunityId) {
        return opportunityRepository.findById(opportunityId);
    }

    @Override
    public Opportunity create(CreateOpportunityDTO createOpportunityDTO)
            throws PositionNotFoundException, UserNotFoundException {
        Opportunity opportunityToCreate = new Opportunity();
        opportunityToCreate.setPosition(positionRepository.findById(createOpportunityDTO.getPositionId())
                .orElseThrow(() -> new PositionNotFoundException(
                        ApiConstants.CANNOT_FIND_POSITION_MESSAGE + createOpportunityDTO.getPositionId())));

        // candidate id check must be implemented here (get request to 'user-service/users')
        opportunityToCreate.setCandidateId(createOpportunityDTO.getCandidateId());

        opportunityToCreate.setStatus(Status.NEW);
        opportunityToCreate.setCvFileName(
                fileService.saveCv(createOpportunityDTO.getCv(), createOpportunityDTO.getPositionId(),
                        createOpportunityDTO.getCandidateId()));
        opportunityToCreate.setCoverLetterFileName(
                fileService.saveCoverLetter(createOpportunityDTO.getCoverLetter(), createOpportunityDTO.getPositionId(),
                        createOpportunityDTO.getCandidateId()));
        return opportunityRepository.saveAndFlush(opportunityToCreate);
    }

}
