package org.example.api.dto;

import lombok.Data;
import org.example.api.constant.ApiConstants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProjectDTO {

    @Size(max = 50, message = ApiConstants.PROJECT_NAME_SIZE_IS_INVALID_MESSAGE)
    @NotNull(message = ApiConstants.PROJECT_NAME_IS_MANDATORY_MESSAGE)
    @NotEmpty(message = ApiConstants.PROJECT_NAME_IS_MANDATORY_MESSAGE)
    private String name;

    @NotNull(message = ApiConstants.CONTACT_ID_IS_MANDATORY_MESSAGE)
    @NotEmpty(message = ApiConstants.CONTACT_ID_IS_MANDATORY_MESSAGE)
    private long contactId;

}
