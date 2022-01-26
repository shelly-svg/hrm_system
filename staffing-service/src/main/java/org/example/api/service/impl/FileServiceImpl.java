package org.example.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.api.entity.Opportunity;
import org.example.api.repository.OpportunityRepository;
import org.example.api.service.FileService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private static final String CV_FOLDER_NAME = "/cvs";
    private static final String COVER_LETTER_FOLDER_NAME = "/coverLetters";
    private static final String FILE_EXTENSION = ".pdf";
    private static final String SLASH = "/";
    private static final String UNDERSCORE = "_";

    private static final String CV_HOLDER_IS_CREATED_MESSAGE = "The folder to store cvs is successfully created, " +
            "path: ";
    private static final String COVER_LETTER_HOLDER_IS_CREATED_MESSAGE = "The folder to store cover letters is " +
            "successfully created, path: ";
    private static final String CANNOT_CREATE_FILE_MESSAGE = "Cannot create new file at the storage: ";

    private final File cvHolder;
    private final File coverLetterHolder;
    private final OpportunityRepository opportunityRepository;

    public FileServiceImpl(Environment environment, OpportunityRepository opportunityRepository) {
        this.cvHolder = new File(
                environment.getProperty("application.files.file-holder-path") + SLASH + CV_FOLDER_NAME);
        this.coverLetterHolder = new File(
                environment.getProperty("application.files.file-holder-path") + SLASH + COVER_LETTER_FOLDER_NAME);
        this.opportunityRepository = opportunityRepository;
    }

    @PostConstruct
    private void setUp() {
        if (cvHolder.mkdirs()) {
            log.info(CV_HOLDER_IS_CREATED_MESSAGE + cvHolder.getAbsolutePath());
        }
        if (coverLetterHolder.mkdir()) {
            log.info(COVER_LETTER_HOLDER_IS_CREATED_MESSAGE + coverLetterHolder.getAbsolutePath());
        }
    }

    @Override
    public String saveCv(MultipartFile cv, long positionId, long candidateId) {
        return storeFile(cv, cvHolder.getAbsolutePath(), positionId + UNDERSCORE + candidateId);
    }

    private String storeFile(MultipartFile fileToStore, String locationToStore, String fileName) {
        File serverFile = new File(locationToStore + SLASH + fileName + FILE_EXTENSION);
        try {
            Files.deleteIfExists(serverFile.toPath());
            if (!serverFile.createNewFile()) {
                throw new IOException(CANNOT_CREATE_FILE_MESSAGE + serverFile.getAbsolutePath());
            }
            Files.copy(fileToStore.getInputStream(), serverFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return serverFile.getName();
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
        return serverFile.getName();
    }

    @Override
    public String saveCoverLetter(MultipartFile coverLetter, long positionId, long candidateId) {
        return storeFile(coverLetter, coverLetterHolder.getAbsolutePath(), positionId + UNDERSCORE + candidateId);
    }

    @Override
    public boolean isCvPresent(long opportunityId) {
        Optional<Opportunity> opportunity = opportunityRepository.findById(opportunityId);
        if (opportunity.isEmpty()) {
            return false;
        }
        return isFilePresent(cvHolder.getAbsolutePath(),
                opportunity.get().getPosition().getId() + UNDERSCORE + opportunity.get().getCandidateId());
    }

    private boolean isFilePresent(String fileLocation, String fileName) {
        File fileToCheck = new File(fileLocation + SLASH + fileName + FILE_EXTENSION);
        return fileToCheck.exists();
    }

    @Override
    public boolean isCoverLetterPresent(long opportunityId) {
        Optional<Opportunity> opportunity = opportunityRepository.findById(opportunityId);
        if (opportunity.isEmpty()) {
            return false;
        }
        return isFilePresent(coverLetterHolder.getAbsolutePath(),
                opportunity.get().getPosition().getId() + UNDERSCORE + opportunity.get().getCandidateId());
    }

}
