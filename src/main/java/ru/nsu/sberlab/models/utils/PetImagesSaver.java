package ru.nsu.sberlab.models.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;
import ru.nsu.sberlab.models.entities.PetImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

public final class PetImagesSaver {
    private static final String FILE_SYSTEM_RESOURCES_PATH = "/src/main/resources/static/img/pet_images";
    private static final String RUNTIME_RESOURCES_PATH = "static/img/pet_images";
    private static final String DEFAULT_PET_IMAGE = "default.jpg";

    public static void saveImageOnFileSystem(MultipartFile imageFile, PetImage petImage) throws IOException {
        File saveFile = new ClassPathResource(RUNTIME_RESOURCES_PATH).getFile();
        if (imageFile.isEmpty()) {
            petImage.setImageUUIDName(DEFAULT_PET_IMAGE);
            petImage.setImagePath(saveFile.getAbsolutePath() + File.separator);
            return;
        }
        updatePetImage(saveFile.getAbsolutePath(), imageFile, petImage);
    }

    public static void replaceImageOnFileSystem(MultipartFile imageFile, PetImage petImage) throws IOException {
        if (imageFile.isEmpty()) {
            return;
        }
        Path absolutePath = Paths.get(".").toAbsolutePath();
        String previousPath = absolutePath + FILE_SYSTEM_RESOURCES_PATH + File.separator;
        deletePreviousPetImageFromFileSystem(previousPath, petImage.getImageUUIDName());
        File saveFile = new ClassPathResource(RUNTIME_RESOURCES_PATH).getFile();
        deletePreviousPetImageFromFileSystem(saveFile.getAbsolutePath() + File.separator, petImage.getImageUUIDName());
        updatePetImage(saveFile.getAbsolutePath(), imageFile, petImage);
    }

    private static void updatePetImage(String saveFilePath, MultipartFile imageFile, PetImage petImage) throws IOException {
        Path absolutePath = Paths.get(".").toAbsolutePath();
        petImage.setImageUUIDName(UUID.randomUUID() + imageFile.getOriginalFilename());
        Path totalFilePath = Paths.get(absolutePath + FILE_SYSTEM_RESOURCES_PATH + File.separator + petImage.getImageUUIDName());
        Files.copy(imageFile.getInputStream(), totalFilePath, StandardCopyOption.REPLACE_EXISTING);

        petImage.setImagePath(saveFilePath + File.separator);
        Path localFilePath = Paths.get(petImage.getImagePath() + petImage.getImageUUIDName());
        Files.copy(imageFile.getInputStream(), localFilePath, StandardCopyOption.REPLACE_EXISTING);
    }

    private static void deletePreviousPetImageFromFileSystem(String previousPath, String fileName) throws IOException {
        if (!Objects.equals(fileName, DEFAULT_PET_IMAGE)) {
            Path previousImagePath = Paths.get(previousPath + fileName);
            Files.deleteIfExists(previousImagePath);
        }
    }

    private PetImagesSaver() {
        throw new IllegalStateException();
    }
}
