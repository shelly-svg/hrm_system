package org.example.api.service;

import org.example.api.dto.CreatePositionDTO;
import org.example.api.entity.Position;
import org.example.api.exception.ProjectNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PositionService {

    /**
     * Returns a position with given {@code positionId}, if there is no such position in positions holder
     * returns an empty optional
     *
     * @param positionId id of the position
     * @return An optional which contains position with given {@code positionId}
     * @see org.example.api.entity.Position
     */
    Optional<Position> getById(long positionId);

    /**
     * Returns positions in given {@code page}
     *
     * @param page            number of page
     * @param entitiesPerPage number of position entities to display per one page
     * @return List of Position objects
     * @see org.example.api.entity.Position
     */
    List<Position> getAll(int page, int entitiesPerPage);

    /**
     * Creates a position in positions holder
     *
     * @param createPositionDTO a create position data transfer object
     * @return Position object
     * @throws ProjectNotFoundException if cannot find a project with id such as used in {@code createPositionDTO}
     * @see org.example.api.dto.CreatePositionDTO
     * @see org.example.api.entity.Position
     */
    Position create(CreatePositionDTO createPositionDTO) throws ProjectNotFoundException;

}
