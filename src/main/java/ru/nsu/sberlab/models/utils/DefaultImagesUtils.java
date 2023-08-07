package ru.nsu.sberlab.models.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class DefaultImagesUtils {
    @Value("${default.pet.image.name}")
    private String defaultPetImage;
}
