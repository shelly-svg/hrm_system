package org.example.api.validator;

import org.example.api.constant.ApiConstants;
import org.example.api.constant.PaginationProperties;
import org.example.api.exception.PaginationParamsIsInvalidException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaginationValidator {

    private static final int MIN_PAGE_NUMBER = 0;

    private final int minEntitiesPerPage;
    private final int maxEntitiesPerPage;

    public PaginationValidator(PaginationProperties paginationProperties) {
        minEntitiesPerPage = paginationProperties.getMinEntitiesPerPage();
        maxEntitiesPerPage = paginationProperties.getMaxEntitiesPerPage();
    }

    /**
     * Validates pagination params
     *
     * @param page            number of page
     * @param entitiesPerPage number of entities to display per one page
     * @throws PaginationParamsIsInvalidException if any or both of pagination params is invalid
     */
    public void validate(int page, int entitiesPerPage) throws PaginationParamsIsInvalidException {
        Map<String, String> errors = new HashMap<>();
        if (page < MIN_PAGE_NUMBER) {
            errors.put(ApiConstants.PAGE_REQUEST_PARAM, ApiConstants.PAGE_IS_INVALID_MESSAGE);
        }
        if (entitiesPerPage < minEntitiesPerPage || entitiesPerPage > maxEntitiesPerPage) {
            errors.put(ApiConstants.ENTITIES_PER_PAGE_REQUEST_PARAM,
                    String.format(ApiConstants.ENTITIES_PER_PAGE_IS_INVALID_MESSAGE, maxEntitiesPerPage,
                            minEntitiesPerPage));
        }
        if (!errors.isEmpty()) {
            throw new PaginationParamsIsInvalidException(errors);
        }
    }

}
