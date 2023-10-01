package ru.nsu.sberlab.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ReCaptchaException extends RuntimeException {
    private final List<String> errors;
    private final String redirectURI;
}
