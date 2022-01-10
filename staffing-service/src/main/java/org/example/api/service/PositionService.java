package org.example.api.service;

import org.example.api.entity.Position;

import java.util.List;
import java.util.Optional;

public interface PositionService {

    /**
     * Returns a position with given {@code positionId}, if there is no such position in positions holder
     * returns an empty optional
     *
     * @param positionId id of the position
     * @return An optional which contains position with given {@code positionId}
     */
    Optional<Position> getById(long positionId);

    /**
     * Returns positions in given {@code page}
     *
     * @param page            number of page
     * @param entitiesPerPage number of position entities to display per one page
     * @return List of Position objects
     */
    List<Position> getAll(int page, int entitiesPerPage);

    /**
     * Creates a position in positions holder
     *
     * @param positionToCreate a position to create
     * @return Position object
     */
    Position create(Position positionToCreate);

}
