package org.example.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * Saves cv file at the server storage
     *
     * @param cv          a cv to save
     * @param positionId  id of the position, used to generate name of the file
     * @param candidateId id of the candidate, used to generate name of the file
     * @return name of the stored file
     */
    String saveCv(MultipartFile cv, long positionId, long candidateId);

    /**
     * Saves cover letter file at the server storage
     *
     * @param coverLetter a cover letter to save
     * @param positionId  id of the position, used to generate name of the file
     * @param candidateId id of the candidate, used to generate name of the file
     * @return name of the stored file
     */
    String saveCoverLetter(MultipartFile coverLetter, long positionId, long candidateId);

}
