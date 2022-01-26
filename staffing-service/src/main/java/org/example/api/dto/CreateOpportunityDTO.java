package org.example.api.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class CreateOpportunityDTO {

    @NotNull(message = "Position id is mandatory")
    private Long positionId;

    @NotNull(message = "Candidate id is mandatory")
    private Long candidateId;

    @NotNull(message = "Cv is mandatory")
    private MultipartFile cv;

    @NotNull(message = "Cover letter is mandatory")
    private MultipartFile coverLetter;

}
