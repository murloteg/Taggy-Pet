package ru.nsu.sberlab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.exceptions.ImageNotFoundException;
import ru.nsu.sberlab.models.dto.PetImageDataDto;
import ru.nsu.sberlab.models.entities.PetImage;
import ru.nsu.sberlab.repositories.PetImageRepository;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final PetImageRepository petImageRepository;

    public PetImageDataDto getPetImageByUUID(String uuidName) {
        PetImage petImage = petImageRepository.findByImageUUIDName(uuidName)
                .orElseThrow(ImageNotFoundException::new);
        return new PetImageDataDto(petImage.getSize(), petImage.getContentType(), petImage.getImageData());
    }
}
