package ru.nsu.sberlab.repositories;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
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
    private String FILE_SYSTEM_IMAGES_PATH;

    @Value("${default.runtime.images.path}")
    private String RUNTIME_IMAGES_PATH;

    @Value("${default.pet.image.name}")
    private String DEFAULT_PET_IMAGE;

    @Value("${pet.images.folder}")
    private String PET_IMAGES_FOLDER;

    /**
     * <p>This method saves pet image in file system.
     * If user didn't send any image from web-form then this method sets default pet image and returns.
     * Otherwise, this method saves new image in the file system and in /target/classes/{RUNTIME_RESOURCES_PATH}.
     * </p>
     *
     * @param imageFile this parameter present MultipartFile submitted from web-form
     * @param petImage  this parameter present pet image entity
     * @throws IOException
     */
    @Override
    public void saveImageOnFileSystem(MultipartFile imageFile, PetImage petImage) throws IOException {
        File runtimeResourcesFolder = ResourceUtils.getFile("classpath:" + RUNTIME_RESOURCES_PATH);
        if (imageFile.isEmpty()) { // FIXME: remove business logic later.
            petImage.setImageUUIDName(DEFAULT_PET_IMAGE);
            return;
        }
        updatePetImage(runtimeResourcesFolder.getAbsolutePath(), imageFile, petImage);
    }

    /**
     * <p>This method replace previous pet's image in file system and in /target/classes/{RUNTIME_RESOURCES_PATH}.
     * If {@imageFile} is empty, then nothing happens and method returns.
     * Otherwise, this method firstly deletes previous image in both directories and then saves new image in the same directories.
     * </p>
     *
     * @param imageFile this parameter present MultipartFile submitted from web-form
     * @param petImage  this parameter present pet image entity
     * @throws IOException
     */
    @Override
    public void replaceImageOnFileSystem(MultipartFile imageFile, PetImage petImage) throws IOException {
        if (imageFile.isEmpty()) { // FIXME: remove business logic later.
            return;
        }
        removeImageFromFileSystem(
                restorePath(FILE_SYSTEM_RESOURCES_PATH),
                petImage.getImageUUIDName()
        );
        File runtimeResourcesFolder = ResourceUtils.getFile("classpath:" + RUNTIME_RESOURCES_PATH);
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

    /**
     * <p>This method checks if default image contains in file system in /target/classes/{RUNTIME_RESOURCES_PATH}.
     * If it is, then nothing happens.
     * Otherwise, this method copies default image from /target/classes/{RUNTIME_IMAGES_PATH} and pastes it.
     * </p>
     * Note: this method also can creates /target/{RUNTIME_IMAGES_PATH}/{PET_IMAGES_FOLDER} if it doesn't exist on file system.
     *
     * @param defaultImageName this parameter present name of default image on file system
     */
    @Override
    public void saveDefaultImageOnFileSystemIfRequired(String defaultImageName) {
        try {
            String pathToImagesDirectory = ResourceUtils.getURL("classpath:" + RUNTIME_IMAGES_PATH).getPath();
            Path petImagesFolderPath = Paths.get(pathToImagesDirectory + File.separator + PET_IMAGES_FOLDER);
            if (!Files.exists(petImagesFolderPath)) { // FIXME: remove business logic later.
                Files.createDirectory(petImagesFolderPath);
                Files.createDirectory(Paths.get(restorePath(FILE_SYSTEM_IMAGES_PATH) + File.separator + PET_IMAGES_FOLDER));
            }
            File runtimeResourcesFolder = ResourceUtils.getFile("classpath:" + RUNTIME_RESOURCES_PATH);
            Path pathToSave = Paths.get(runtimeResourcesFolder.getAbsolutePath() + File.separator + defaultImageName);
            if (!Files.exists(pathToSave)) { // FIXME: remove business logic later.
                Path defaultImagePath = Paths.get(restorePath(FILE_SYSTEM_IMAGES_PATH) + File.separator + defaultImageName);
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
