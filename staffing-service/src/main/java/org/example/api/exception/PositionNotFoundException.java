package org.example.api.exception;

public class PositionNotFoundException extends Exception {

    private static final long serialVersionUID = -7213932484328432432L;

    public PositionNotFoundException(String message) {
        super(message);
    }

    public PositionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
