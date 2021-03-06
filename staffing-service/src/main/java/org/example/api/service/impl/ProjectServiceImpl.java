package org.example.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.dto.CreateProjectDTO;
import org.example.api.entity.Project;
import org.example.api.mapper.ProjectMapper;
import org.example.api.repository.ProjectRepository;
import org.example.api.service.ProjectService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public Optional<Project> getById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public List<Project> getAll(int page, int entitiesPerPage) {
        return projectRepository.findAll(PageRequest.of(page, entitiesPerPage)).toList();
    }

    @Override
    public Project create(CreateProjectDTO createProjectDTO) {
        return projectRepository.saveAndFlush(projectMapper.toProject(createProjectDTO));
    }

}
