package ru.nsu.sberlab.exceptions;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ReCaptchaException extends RuntimeException {
    private final List<String> errors;
    private final String redirectURI;

    public List<String> getErrors() {
        return errors;
    }

    public String getRedirectURI() {
        if (redirectURI.isEmpty() || redirectURI.charAt(0) != '/') {
            return "/".concat(redirectURI);
        }
        return redirectURI;
    }
}
