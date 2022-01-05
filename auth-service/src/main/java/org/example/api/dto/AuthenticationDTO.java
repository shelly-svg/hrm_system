package org.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.example.api.constant.ApiConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class AuthenticationDTO {

    @Size(min = 4, max = 20, message = ApiConstants.USERNAME_INVALIDITY_MESSAGE)
    @NotNull(message = ApiConstants.USERNAME_IS_MANDATORY_MESSAGE)
    @NotBlank(message = ApiConstants.USERNAME_IS_MANDATORY_MESSAGE)
    private String username;

    @Size(min = 4, max = 30, message = ApiConstants.PASSWORD_INVALIDITY_MESSAGE)
    @NotNull(message = ApiConstants.PASSWORD_IS_MANDATORY_MESSAGE)
    @NotBlank(message = ApiConstants.PASSWORD_IS_MANDATORY_MESSAGE)
    @ToString.Exclude
    private String password;

}
