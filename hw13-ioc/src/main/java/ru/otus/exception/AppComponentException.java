package ru.otus.exception;

public class AppComponentException extends RuntimeException {

    public AppComponentException(String message) {
        super(message);
    }

    public AppComponentException(String message, Throwable cause) {
        super(message, cause);
    }
}
