package org.example.api.service;

import org.example.api.entity.Opportunity;

import java.util.Optional;

public interface OpportunityService {

    Optional<Opportunity> getById(long opportunityId);

    Opportunity create(Opportunity opportunityToCreate);

}
