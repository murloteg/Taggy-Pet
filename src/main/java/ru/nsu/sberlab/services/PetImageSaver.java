package ru.nsu.sberlab.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nsu.sberlab.models.entities.PetImage;
import ru.nsu.sberlab.models.utils.ImageRemover;
import ru.nsu.sberlab.models.utils.PathResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@NoArgsConstructor
public class PetImageSaver {
    @Value("${default.file.system.resources.path}")
    private String FILE_SYSTEM_RESOURCES_PATH;

    @Value("${default.runtime.resources.path}")
    private String RUNTIME_RESOURCES_PATH;

    @Value("${default.pet.image.name}")
    private String DEFAULT_PET_IMAGE;

    public void saveImageOnFileSystem(MultipartFile imageFile, PetImage petImage) throws IOException {
        File saveFile = new ClassPathResource(RUNTIME_RESOURCES_PATH).getFile();
        if (imageFile.isEmpty()) {
            petImage.setImageUUIDName(DEFAULT_PET_IMAGE);
            return;
        }
        updatePetImage(saveFile.getAbsolutePath(), imageFile, petImage);
    }

    public void replaceImageOnFileSystem(MultipartFile imageFile, PetImage petImage) throws IOException {
        if (imageFile.isEmpty()) {
            return;
        }
        ImageRemover.removeImageFromFileSystem(
                PathResolver.resolvePath(FILE_SYSTEM_RESOURCES_PATH),
                petImage.getImageUUIDName(),
                DEFAULT_PET_IMAGE
        );
        File saveFile = new ClassPathResource(RUNTIME_RESOURCES_PATH).getFile();
        ImageRemover.removeImageFromFileSystem(
                saveFile.getAbsolutePath() + File.separator,
                petImage.getImageUUIDName(),
                DEFAULT_PET_IMAGE
        );
        updatePetImage(saveFile.getAbsolutePath(), imageFile, petImage);
    }

    private void updatePetImage(String saveFilePath, MultipartFile imageFile, PetImage petImage) throws IOException {
        petImage.setImageUUIDName(UUID.randomUUID() + imageFile.getOriginalFilename());
        Path totalFilePath = Paths.get(PathResolver.resolvePath(FILE_SYSTEM_RESOURCES_PATH) + petImage.getImageUUIDName());
        Files.copy(imageFile.getInputStream(), totalFilePath, StandardCopyOption.REPLACE_EXISTING);

        Path localFilePath = Paths.get(saveFilePath + File.separator + petImage.getImageUUIDName());
        Files.copy(imageFile.getInputStream(), localFilePath, StandardCopyOption.REPLACE_EXISTING);
    }
}
