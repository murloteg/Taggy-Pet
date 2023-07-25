package ru.nsu.sberlab.exceptions;

public class PropertyNotFoundException extends RuntimeException {
    private final String message;

    public PropertyNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
