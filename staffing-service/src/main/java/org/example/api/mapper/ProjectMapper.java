package org.example.api.mapper;

import org.example.api.dto.ProjectDTO;
import org.example.api.entity.Project;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectMapper {

    Project extract(ProjectDTO projectDTO);

}
