package ru.nsu.sberlab.models.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.repositories.FeaturesRepository;

@Service
@RequiredArgsConstructor
public class FeatureCleaner {
    private final FeaturesRepository featuresRepository;

    public void clear(Feature feature) {
        if (feature.getPets().isEmpty()) {
            featuresRepository.deleteById(feature.getFeatureId());
        }
    }
}
