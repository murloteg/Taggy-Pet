package ru.nsu.sberlab.exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

public class FailedUserCreationException extends RuntimeException {
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @Override
    public String getMessage() {
        return bundle.getString("api.chipped-pets-helper.server.error.user-not-created");
    }
}
