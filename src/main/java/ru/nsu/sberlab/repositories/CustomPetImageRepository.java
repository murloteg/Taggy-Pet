package ru.nsu.sberlab.repositories;

import org.springframework.web.multipart.MultipartFile;
import ru.nsu.sberlab.models.entities.PetImage;

import java.io.IOException;

public interface CustomPetImageRepository extends CustomImageRepository {
    void saveImageOnFileSystem(MultipartFile imageFile, PetImage petImage) throws IOException;
    void replaceImageOnFileSystem(MultipartFile imageFile, PetImage petImage) throws IOException;
    void updatePetImage(String saveFilePath, MultipartFile imageFile, PetImage petImage) throws IOException;
    void removePetImage(PetImage petImage) throws IOException;
}
