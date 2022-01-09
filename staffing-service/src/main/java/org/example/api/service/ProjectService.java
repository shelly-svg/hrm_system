package org.example.api.service;

import org.example.api.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Optional<Project> getById(Long projectId);

    List<Project> getAll(int page, int entitiesPerPage);

    Project create(Project projectToCreate);

}
