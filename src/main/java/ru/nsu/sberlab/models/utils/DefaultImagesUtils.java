package ru.nsu.sberlab.models.utils;

import org.springframework.beans.factory.annotation.Value;

public class DefaultImagesUtils {
    @Value("${default.pet.image.name}")
    private String defaultPetImage;

    public String getDefaultPetImageName() {
           return defaultPetImage;
    }
}
