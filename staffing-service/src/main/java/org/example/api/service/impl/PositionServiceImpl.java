package org.example.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.entity.Position;
import org.example.api.repository.PositionRepository;
import org.example.api.service.PositionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    public Optional<Position> getById(long positionId) {
        return positionRepository.findById(positionId);
    }

    @Override
    public List<Position> getAll(int page, int entitiesPerPage) {
        return positionRepository.findAll(PageRequest.of(page, entitiesPerPage)).toList();
    }

    @Override
    public Position create(Position positionToCreate) {
        return positionRepository.saveAndFlush(positionToCreate);
    }

}
