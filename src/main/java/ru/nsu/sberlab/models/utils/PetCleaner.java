package ru.nsu.sberlab.models.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.exceptions.FileSystemErrorException;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.PetImage;
import ru.nsu.sberlab.repositories.PetRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetCleaner {
    private final PetRepository petRepository;
    private final FeatureCleaner featureCleaner;

    @Value("${default.file.system.resources.path}")
    private String FILE_SYSTEM_RESOURCES_PATH;

    @Value("${default.runtime.resources.path}")
    private String RUNTIME_RESOURCES_PATH;

    @Value("${default.pet.image.name}")
    private String DEFAULT_PET_IMAGE;

    @Transactional
    public void clear(Pet pet) {
        if (pet.getUsers().isEmpty()) {
            removePetImage(pet.getPetImage());
            List<Feature> features = pet.getFeatures();
            petRepository.deleteByChipId(pet.getChipId());
            features.forEach(feature -> feature.getPets().remove(pet));
            features.forEach(featureCleaner::clear);
        }
    }

    private void removePetImage(PetImage petImage) {
        try {
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
        } catch (IOException exception) {
            throw new FileSystemErrorException(exception.getMessage());
        }
    }
}
