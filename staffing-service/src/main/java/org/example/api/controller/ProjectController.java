package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.constant.ApiConstants;
import org.example.api.dto.CreateProjectDTO;
import org.example.api.entity.Project;
import org.example.api.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Validated
public class ProjectController {

    private static final int MIN_ENTITIES_PER_PAGE = 5;
    private static final int MAX_ENTITIES_PER_PAGE = 50;
    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_ENTITIES_PER_PAGE = "10";
    private static final String ENTITIES_PER_PAGE_IS_INVALID_MESSAGE = "Entities per page must be higher than 4 and " +
            "lower than 51";

    private final ProjectService projectService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> getById(@PathVariable(ApiConstants.ID_PATH_PARAM) long projectId) {
        Optional<Project> requestedProject = projectService.getById(projectId);
        return requestedProject.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Project>> getAll(
            @RequestParam(value = ApiConstants.PAGE_REQUEST_PARAM, required = false, defaultValue = DEFAULT_PAGE)
                    @PositiveOrZero(message = ApiConstants.PAGE_IS_INVALID_MESSAGE) Integer page,
            @RequestParam(value = ApiConstants.ENTITIES_PER_PAGE_REQUEST_PARAM, required = false,
                    defaultValue = DEFAULT_ENTITIES_PER_PAGE)
            @Min(value = MIN_ENTITIES_PER_PAGE, message = ENTITIES_PER_PAGE_IS_INVALID_MESSAGE)
            @Max(value = MAX_ENTITIES_PER_PAGE, message = ENTITIES_PER_PAGE_IS_INVALID_MESSAGE) Integer entitiesPerPage) {
        return ResponseEntity.ok(projectService.getAll(page, entitiesPerPage));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> create(@RequestBody @Valid CreateProjectDTO createProjectDTO) {
        return new ResponseEntity<>(projectService.create(createProjectDTO), HttpStatus.CREATED);
    }

}
