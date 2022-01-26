package org.example.api.mapper;

import org.example.api.dto.CreateProjectDTO;
import org.example.api.entity.Project;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectMapper {

    Project toProject(CreateProjectDTO createProjectDTO);

}
