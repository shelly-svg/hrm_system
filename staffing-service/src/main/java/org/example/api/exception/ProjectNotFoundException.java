package org.example.api.exception;

public class ProjectNotFoundException extends Exception {

    private static final long serialVersionUID = 424783247248432324L;

    public ProjectNotFoundException(String message) {
        super(message);
    }

    public ProjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
