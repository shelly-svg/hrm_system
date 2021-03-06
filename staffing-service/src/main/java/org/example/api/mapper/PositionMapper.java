package org.example.api.mapper;

import org.example.api.dto.CreatePositionDTO;
import org.example.api.entity.Position;
import org.mapstruct.Mapper;

@Mapper
public interface PositionMapper {

    Position toPosition(CreatePositionDTO createPositionDTO);

}
