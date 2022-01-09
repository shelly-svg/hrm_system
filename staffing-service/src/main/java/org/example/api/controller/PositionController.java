package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.constant.ApiConstants;
import org.example.api.constant.PaginationProperties;
import org.example.api.dto.PositionDTO;
import org.example.api.entity.Position;
import org.example.api.exception.PaginationParamsIsInvalidException;
import org.example.api.exception.ProjectNotFoundException;
import org.example.api.mapper.PositionMapper;
import org.example.api.service.PositionService;
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
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
public class PositionController {

    private final ProjectService projectService;
    private final PositionService positionService;
    private final PaginationValidator paginationValidator;
    private final PaginationProperties paginationProperties;
    private final PositionMapper positionMapper;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Position> getById(@PathVariable(ApiConstants.ID_PATH_PARAM) long positionId) {
        Optional<Position> requestedPosition = positionService.getById(positionId);
        return requestedPosition.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Position>> getAll(
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
        return ResponseEntity.ok(positionService.getAll(page, entitiesPerPage));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Position> create(@RequestBody @Valid PositionDTO positionDTO)
            throws ProjectNotFoundException {
        Position positionToCreate = positionMapper.extract(positionDTO);
        positionToCreate.setProject(projectService.getById(positionDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException(
                        ApiConstants.CANNOT_FIND_PROJECT_MESSAGE + positionDTO.getProjectId())));
        return new ResponseEntity<>(positionService.create(positionToCreate), HttpStatus.CREATED);
    }

}
