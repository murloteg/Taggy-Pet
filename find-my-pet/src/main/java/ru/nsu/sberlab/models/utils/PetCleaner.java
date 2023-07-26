package ru.nsu.sberlab.models.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.repositories.PetRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetCleaner {
    private final PetRepository petRepository;
    private final FeatureCleaner featureCleaner;

    @Transactional
    public void clear(Pet pet) {
        if (pet.getUsers().isEmpty()) {
            List<Feature> features = pet.getFeatures();
            petRepository.deleteByChipId(pet.getChipId());
            features.forEach(feature -> feature.getPets().remove(pet));
            features.forEach(featureCleaner::clear);
        }
    }
}
