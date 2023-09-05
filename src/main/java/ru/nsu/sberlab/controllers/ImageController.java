package ru.nsu.sberlab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.nsu.sberlab.exceptions.ImageNotFoundException;
import ru.nsu.sberlab.models.entities.PetImage;
import ru.nsu.sberlab.repositories.PetImageRepository;

import java.io.ByteArrayInputStream;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final PetImageRepository petImageRepository;

    @GetMapping("/pet/images/{uuidName}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getPetImageByUUIDName(@PathVariable String uuidName) {
        PetImage petImage = petImageRepository.findByImageUUIDName(uuidName).orElseThrow(ImageNotFoundException::new);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(petImage.getContentType()))
                .contentLength(petImage.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(petImage.getImageData())));
    }
}
