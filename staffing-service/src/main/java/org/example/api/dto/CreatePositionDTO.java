package org.example.api.dto;

import lombok.Data;
import org.example.api.constant.ApiConstants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreatePositionDTO {

    private long projectId;

    @Size(max = 50, message = ApiConstants.JOB_FUNCTION_SIZE_IS_INVALID_MESSAGE)
    @NotNull(message = ApiConstants.JOB_FUNCTION_IS_MANDATORY_MESSAGE)
    @NotEmpty(message = ApiConstants.JOB_FUNCTION_IS_MANDATORY_MESSAGE)
    private String jobFunction;

    @Size(max = 50, message = ApiConstants.WORK_FORMAT_SIZE_IS_INVALID_MESSAGE)
    @NotNull(message = ApiConstants.WORK_FORMAT_IS_MANDATORY_MESSAGE)
    @NotEmpty(message = ApiConstants.WORK_FORMAT_IS_MANDATORY_MESSAGE)
    private String workFormat;

    @Size(max = 15, message = ApiConstants.ENGLISH_LEVEL_SIZE_IS_INVALID_MESSAGE)
    @NotNull(message = ApiConstants.ENGLISH_LEVEL_IS_MANDATORY_MESSAGE)
    @NotEmpty(message = ApiConstants.ENGLISH_LEVEL_IS_MANDATORY_MESSAGE)
    private String englishLevel;

    @NotNull(message = ApiConstants.DESCRIPTION_IS_MANDATORY_MESSAGE)
    @NotEmpty(message = ApiConstants.DESCRIPTION_IS_MANDATORY_MESSAGE)
    private String description;

}
