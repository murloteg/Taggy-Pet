package ru.nsu.sberlab.models.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.repositories.FeaturesRepository;

@Service
@RequiredArgsConstructor
public class FeatureCleaner {
    private final FeaturesRepository featuresRepository;

    @Transactional
    public void clear(Feature feature) {
        if (feature.getPets().isEmpty()) {
            featuresRepository.deleteFeatureByFeatureId(feature.getFeatureId());
        }
    }
}
