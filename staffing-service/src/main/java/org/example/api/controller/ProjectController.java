package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.constant.ApiConstants;
import org.example.api.constant.PaginationProperties;
import org.example.api.dto.ProjectDTO;
import org.example.api.entity.Project;
import org.example.api.exception.PaginationParamsIsInvalidException;
import org.example.api.mapper.ProjectMapper;
import org.example.api.service.ProjectService;
import org.example.api.validator.PaginationValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final PaginationValidator paginationValidator;
    private final PaginationProperties paginationProperties;
    private final ProjectMapper projectMapper;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> getById(@PathVariable(ApiConstants.ID_PATH_PARAM) long projectId) {
        Optional<Project> requestedProject = projectService.getById(projectId);
        return requestedProject.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Project>> getAll(
            @RequestParam(value = ApiConstants.PAGE_REQUEST_PARAM, required = false) Integer page,
            @RequestParam(value = ApiConstants.ENTITIES_PER_PAGE_REQUEST_PARAM, required = false) Integer entitiesPerPage)
            throws PaginationParamsIsInvalidException {
        if (Objects.isNull(page)) {
            page = paginationProperties.getDefaultPage();
        }
        if (Objects.isNull(entitiesPerPage)) {
            entitiesPerPage = paginationProperties.getDefaultEntitiesPerPage();
        }
        paginationValidator.validate(page, entitiesPerPage);
        return ResponseEntity.ok(projectService.getAll(page, entitiesPerPage));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> create(@RequestBody @Valid ProjectDTO projectDTO) {
        return new ResponseEntity<>(projectService.create(projectMapper.extract(projectDTO)), HttpStatus.CREATED);
    }

}
