package org.example.api.service;

import org.example.api.entity.Position;

import java.util.List;
import java.util.Optional;

public interface PositionService {

    Optional<Position> getById(long positionId);

    List<Position> getAll(int page, int entitiesPerPage);

    Position create(Position positionToCreate);

}
