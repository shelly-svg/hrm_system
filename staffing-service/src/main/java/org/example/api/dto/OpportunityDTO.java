package org.example.api.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OpportunityDTO {

    private long positionId;
    private long candidateId;
    private MultipartFile cv;
    private MultipartFile coverLetter;

}
