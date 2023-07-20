package ru.nsu.sberlab.exceptions;

public class FailedPetSearchException extends RuntimeException {
    private final String chipId;

    public FailedPetSearchException(String chipId) {
        this.chipId = chipId;
    }

    @Override
    public String getMessage() {
        return chipId;
    }
}
