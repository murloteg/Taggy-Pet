package ru.nsu.sberlab.models.enums;

import ru.nsu.sberlab.utils.UtilConsts;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Sex {
    FEMALE,
    MALE;

    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    public static Sex convertStringToSex(String sex) {
        switch (sex) {
            case UtilConsts.StringConsts.MALE -> {
                return MALE;
            }
            case UtilConsts.StringConsts.FEMALE -> {
                return FEMALE;
            }
            default -> {
                throw new IllegalArgumentException(bundle.getString("api.chipped-pets-helper.server.error.invalid-argument"));
            }
        }
    }
}
