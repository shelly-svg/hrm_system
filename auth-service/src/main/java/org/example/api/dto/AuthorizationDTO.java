package org.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.api.constant.ApiConstants;
import org.example.api.entity.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AuthorizationDTO {

    @NotNull(message = ApiConstants.TOKEN_IS_MANDATORY_MESSAGE)
    @NotBlank(message = ApiConstants.TOKEN_IS_MANDATORY_MESSAGE)
    private String token;

    @NotNull(message = ApiConstants.ROLE_IS_MANDATORY_MESSAGE)
    private Role role;

}
