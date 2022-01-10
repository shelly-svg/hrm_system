package org.example.api.service;

import org.example.api.entity.Opportunity;

import java.util.Optional;

public interface OpportunityService {

    /**
     * Returns an optional which contains opportunity with given {@code opportunityId}, if there is no such
     * opportunity in opportunities holder returns an empty optional
     *
     * @param opportunityId id of the opportunity
     * @return An optional which contains opportunity with given {@code opportunityId}
     */
    Optional<Opportunity> getById(long opportunityId);

    /**
     * Creates an opportunity in opportunities holder
     *
     * @param opportunityToCreate an opportunity to create
     * @return Opportunity object
     */
    Opportunity create(Opportunity opportunityToCreate);

}
