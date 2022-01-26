package org.example.api.service;

import org.example.api.dto.CreateProjectDTO;
import org.example.api.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    /**
     * Returns a project with given {@code projectId}, if there is no such project in projects holder
     * returns an empty optional
     *
     * @param projectId id of the project
     * @return An optional which contains project with given {@code projectId}
     * @see org.example.api.entity.Project
     */
    Optional<Project> getById(Long projectId);

    /**
     * Returns projects in given {@code page}
     *
     * @param page            number of page
     * @param entitiesPerPage number of project entities to display per one page
     * @return List of Project objects
     * @see org.example.api.entity.Project
     */
    List<Project> getAll(int page, int entitiesPerPage);

    /**
     * Creates a project in projects holder
     *
     * @param createProjectDTO a create project data transfer object
     * @return Project object
     * @see org.example.api.dto.CreateProjectDTO
     * @see org.example.api.entity.Project
     */
    Project create(CreateProjectDTO createProjectDTO);

}
