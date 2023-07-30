package ru.nsu.sberlab.exceptions;

public class FileSystemErrorException extends RuntimeException {
    public FileSystemErrorException(String message) {
        super(message);
    }
}
