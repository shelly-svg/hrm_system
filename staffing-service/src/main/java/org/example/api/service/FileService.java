package org.example.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveCv(MultipartFile cv, long positionId, long candidateId);

    String saveCoverLetter(MultipartFile coverLetter, long positionId, long candidateId);

}
