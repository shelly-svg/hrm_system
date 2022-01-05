package org.example.api.mapper;

import org.example.api.dto.TokenDTO;
import org.example.api.entity.Token;
import org.mapstruct.Mapper;

@Mapper
public interface TokenMapper {

    /**
     * Extracts token entity from {@code tokenDTO}
     *
     * @param tokenDTO token data transfer object
     * @return Token object
     */
    Token extractToken(TokenDTO tokenDTO);

}
