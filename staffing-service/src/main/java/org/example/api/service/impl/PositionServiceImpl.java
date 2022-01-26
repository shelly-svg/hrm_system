package org.example.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.constant.ApiConstants;
import org.example.api.dto.CreatePositionDTO;
import org.example.api.entity.Position;
import org.example.api.exception.ProjectNotFoundException;
import org.example.api.mapper.PositionMapper;
import org.example.api.repository.PositionRepository;
import org.example.api.repository.ProjectRepository;
import org.example.api.service.PositionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final ProjectRepository projectRepository;
    private final PositionMapper positionMapper;

    @Override
    public Optional<Position> getById(long positionId) {
        return positionRepository.findById(positionId);
    }

    @Override
    public List<Position> getAll(int page, int entitiesPerPage) {
        return positionRepository.findAll(PageRequest.of(page, entitiesPerPage)).toList();
    }

    @Override
    public Position create(CreatePositionDTO createPositionDTO) throws ProjectNotFoundException {
        Position positionToCreate = positionMapper.toPosition(createPositionDTO);
        positionToCreate.setProject(projectRepository.findById(createPositionDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException(
                        ApiConstants.CANNOT_FIND_PROJECT_MESSAGE + createPositionDTO.getProjectId())));
        return positionRepository.saveAndFlush(positionToCreate);
    }

}
