package org.example.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.entity.Opportunity;
import org.example.api.repository.OpportunityRepository;
import org.example.api.service.OpportunityService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpportunityServiceImpl implements OpportunityService {

    private final OpportunityRepository opportunityRepository;

    @Override
    public Optional<Opportunity> getById(long opportunityId) {
        return opportunityRepository.findById(opportunityId);
    }

    @Override
    public Opportunity create(Opportunity opportunityToCreate) {
        return opportunityRepository.saveAndFlush(opportunityToCreate);
    }

}
