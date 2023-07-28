package ru.nsu.sberlab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.entities.FeatureProperty;
import ru.nsu.sberlab.repositories.FeaturePropertiesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeaturePropertiesService {
    private final FeaturePropertiesRepository featurePropertiesRepository;

    public List<FeatureProperty> properties() {
        return featurePropertiesRepository.findAll();
    }
}
