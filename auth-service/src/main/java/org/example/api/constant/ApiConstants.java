package org.example.api.constant;

public final class ApiConstants {

    public static final String ERROR_API_NAME = "api";
    public static final String INCORRECT_USERNAME_OR_PWD_MESSAGE = "Incorrect username or password";
    public static final String USER_IS_BLOCKED_MESSAGE = "User is blocked";
    public static final String AUTHORIZATION_TOKEN_IS_MISSING_MESSAGE = "Authorization token is missing";

    public static final String JWT_HANDLER_EXCEPTION_RESOLVER_BEAN = "jwtExceptionResolver";

    private ApiConstants() {
        throw new UnsupportedOperationException();
    }

}
