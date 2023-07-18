package ru.nsu.sberlab.models.mappers;

import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.FeatureDto;
import ru.nsu.sberlab.models.dto.PropertyTypeDto;
import ru.nsu.sberlab.models.entities.Feature;

import java.util.function.Function;

@Service
public class FeatureDtoMapper implements Function<Feature, FeatureDto> {
    @Override
    public FeatureDto apply(Feature feature) {
        return new FeatureDto(
                feature.getDescription(),
                feature.getDateTime(),
                new PropertyTypeDto(feature.getProperty().getPropertyValue())
        );
    }
}
