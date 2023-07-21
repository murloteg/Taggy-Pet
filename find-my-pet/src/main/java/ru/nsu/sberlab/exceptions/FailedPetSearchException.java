package ru.nsu.sberlab.exceptions;

public class FailedPetSearchException extends RuntimeException {
    private final String message;

    public FailedPetSearchException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
