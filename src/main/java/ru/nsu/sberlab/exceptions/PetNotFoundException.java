package ru.nsu.sberlab.exceptions;

public class PetNotFoundException extends RuntimeException {
    private final String message;

    public PetNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
