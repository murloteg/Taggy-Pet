package ru.nsu.sberlab.repositories;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.nsu.sberlab.exceptions.FileSystemErrorException;
import ru.nsu.sberlab.models.entities.PetImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Repository
public class PetImageRepositoryImpl implements CustomPetImageRepository {
    @Value("${default.file.system.resources.pet.images.path}")
    private String FILE_SYSTEM_RESOURCES_PATH;

    @Value("${default.runtime.resources.pet.images.path}")
    private String RUNTIME_RESOURCES_PATH;

    @Value("${default.file.system.images.path}")
    private String IMAGES_PATH;

    @Value("${default.pet.image.name}")
    private String DEFAULT_PET_IMAGE;

    @Override
    public void saveImageOnFileSystem(MultipartFile imageFile, PetImage petImage) throws IOException {
        File runtimeResourcesFolder = ResourceUtils.getFile("classpath:" + RUNTIME_RESOURCES_PATH);
        if (imageFile.isEmpty()) {
            petImage.setImageUUIDName(DEFAULT_PET_IMAGE);
            return;
        }
        updatePetImage(runtimeResourcesFolder.getAbsolutePath(), imageFile, petImage);
    }

    @Override
    public void replaceImageOnFileSystem(MultipartFile imageFile, PetImage petImage) throws IOException {
        if (imageFile.isEmpty()) {
            return;
        }
        removeImageFromFileSystem(
                restorePath(FILE_SYSTEM_RESOURCES_PATH),
                petImage.getImageUUIDName()
        );
        File runtimeResourcesFolder = new ClassPathResource(RUNTIME_RESOURCES_PATH).getFile();
        removeImageFromFileSystem(
                runtimeResourcesFolder.getAbsolutePath() + File.separator,
                petImage.getImageUUIDName()
        );
        updatePetImage(runtimeResourcesFolder.getAbsolutePath(), imageFile, petImage);
    }

    @Override
    public void updatePetImage(String saveFilePath, MultipartFile imageFile, PetImage petImage) throws IOException {
        petImage.setImageUUIDName(UUID.randomUUID() + imageFile.getOriginalFilename());
        Path totalFilePath = Paths.get(restorePath(FILE_SYSTEM_RESOURCES_PATH) + petImage.getImageUUIDName());
        Files.copy(imageFile.getInputStream(), totalFilePath, StandardCopyOption.REPLACE_EXISTING);

        Path localFilePath = Paths.get(saveFilePath + File.separator + petImage.getImageUUIDName());
        Files.copy(imageFile.getInputStream(), localFilePath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void removePetImage(PetImage petImage) throws IOException {
        removeImageFromFileSystem(
                restorePath(FILE_SYSTEM_RESOURCES_PATH),
                petImage.getImageUUIDName()
        );
        File runtimeResourcesFolder = ResourceUtils.getFile("classpath:" + RUNTIME_RESOURCES_PATH);
        removeImageFromFileSystem(
                runtimeResourcesFolder.getAbsolutePath() + File.separator,
                petImage.getImageUUIDName()
        );
    }

    @Override
    public void removeImageFromFileSystem(String partOfPathToRemove, String fileName) throws IOException {
        if (!Objects.equals(fileName, DEFAULT_PET_IMAGE)) {
            Path imagePath = Paths.get(partOfPathToRemove + fileName);
            Files.deleteIfExists(imagePath);
        }
    }

    @Override
    public String restorePath(String partOfPath) {
        return Paths.get(".").toAbsolutePath() + partOfPath + File.separator;
    }

    @Override
    public void saveDefaultImageOnFileSystemIfRequired(String defaultImageName) {
        try {
            File runtimeResourcesFolder = ResourceUtils.getFile("classpath:" + RUNTIME_RESOURCES_PATH);
            Path pathToSave = Paths.get(runtimeResourcesFolder.getAbsolutePath() + File.separator + defaultImageName);
            if (!Files.exists(pathToSave)) {
                Path defaultImagePath = Paths.get(restorePath(IMAGES_PATH) + File.separator + defaultImageName);
                Files.copy(defaultImagePath, pathToSave, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException exception) {
            throw new FileSystemErrorException(exception.getMessage());
        }
    }

    @PostConstruct
    private void initialization() {
        saveDefaultImageOnFileSystemIfRequired(DEFAULT_PET_IMAGE);
    }
}
