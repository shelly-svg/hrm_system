package org.example.api.service;

import org.example.api.dto.CreateOpportunityDTO;
import org.example.api.entity.Opportunity;
import org.example.api.exception.PositionNotFoundException;
import org.example.api.exception.UserNotFoundException;

import java.util.Optional;

public interface OpportunityService {

    /**
     * Returns an optional which contains opportunity with given {@code opportunityId}, if there is no such
     * opportunity in opportunities holder returns an empty optional
     *
     * @param opportunityId id of the opportunity
     * @return An optional which contains opportunity with given {@code opportunityId}
     * @see org.example.api.entity.Opportunity
     */
    Optional<Opportunity> getById(long opportunityId);

    /**
     * Creates an opportunity in opportunities holder
     *
     * @param createOpportunityDTO a create opportunity data transfer object
     * @return Opportunity object
     * @throws PositionNotFoundException if cannot find a position with id such as used in {@code createOpportunityDTO}
     * @throws UserNotFoundException     if cannot find an user with id such as used in {@code createOpportunityDTO}
     * @see org.example.api.dto.CreateOpportunityDTO
     * @see org.example.api.entity.Opportunity
     */
    Opportunity create(CreateOpportunityDTO createOpportunityDTO)
            throws PositionNotFoundException, UserNotFoundException;

}
