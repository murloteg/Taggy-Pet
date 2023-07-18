package ru.nsu.sberlab.exceptions;

public class PropertyTypeNotFoundException extends RuntimeException {
    private final String message;

    public PropertyTypeNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
