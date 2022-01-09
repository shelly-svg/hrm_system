package org.example.api.constant;

public final class ApiConstants {

    public static final String ID_PATH_PARAM = "id";
    public static final String PAGE_REQUEST_PARAM = "page";
    public static final String ENTITIES_PER_PAGE_REQUEST_PARAM = "entitiesPerPage";

    public static final String PAGE_IS_INVALID_MESSAGE = "Page number must be higher than zero";
    public static final String ENTITIES_PER_PAGE_IS_INVALID_MESSAGE = "Entities per page must be higher than %s and " +
            "below %s";

    public static final String PROJECT_NAME_SIZE_IS_INVALID_MESSAGE = "Project name cannot be longer than 50 symbols";
    public static final String PROJECT_NAME_IS_MANDATORY_MESSAGE = "Project name is mandatory";
    public static final String CONTACT_ID_IS_MANDATORY_MESSAGE = "Contact id is mandatory";

    private ApiConstants() {
        throw new UnsupportedOperationException();
    }

}
