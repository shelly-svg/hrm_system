package org.example.api.constant;

public final class ApiConstants {

    public static final String ERROR_API_NAME = "api";
    public static final String INCORRECT_USERNAME_OR_PWD_MESSAGE = "Incorrect username or password";
    public static final String USER_IS_BLOCKED_MESSAGE = "User is blocked";

    public static final String AUTHENTICATION_DTO_USERNAME_FIELD = "username";
    public static final String AUTHENTICATION_DTO_PASSWORD_FIELD = "password";
    public static final String USERNAME_INVALIDITY_MESSAGE = "Username must be 4 - 20 characters long";
    public static final String USERNAME_IS_MANDATORY_MESSAGE = "Username is mandatory";
    public static final String PASSWORD_INVALIDITY_MESSAGE = "Password must be 4 - 30 characters long";
    public static final String PASSWORD_IS_MANDATORY_MESSAGE = "Password is mandatory";

    public static final String AUTHORIZATION_TOKEN_HEADER_NAME = "Authorization";
    public static final String AUTHORIZATION_DTO_TOKEN_FIELD = "token";
    public static final String AUTHORIZATION_DTO_ROLE_FIELD = "role";
    public static final String TOKEN_IS_MANDATORY_MESSAGE = "Token is mandatory";
    public static final String ROLE_IS_MANDATORY_MESSAGE = "Role is mandatory";

    public static final String USER_DOES_NOT_EXIST_MESSAGE = "User does not exist";

    public static final String USER_DOES_NOT_HAVE_ACCESS_MESSAGE = "This user does not have access to the chosen " +
            "resource";
    public static final String AUTH_TOKEN_IS_EXPIRED_MESSAGE = "Authorization token is expired";
    public static final String AUTH_TOKEN_IS_INVALID_MESSAGE = "Authorization token is invalid";
    public static final String AUTH_TOKEN_IS_MALFORMED_MESSAGE = "Authorization token is malformed";
    public static final String AUTH_TOKEN_IS_MISSING_MESSAGE = "Authorization token is missing";
    public static final String AUTH_TOKEN_IS_BLACKLISTED_MESSAGE = "Authorization token is blacklisted";

    public static final String ROLE_CLAIMS_FIELD = "role";

    private ApiConstants() {
        throw new UnsupportedOperationException();
    }

}
