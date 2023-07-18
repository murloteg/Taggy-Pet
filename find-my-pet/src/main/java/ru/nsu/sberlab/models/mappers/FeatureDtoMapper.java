package ru.nsu.sberlab.models.mappers;

import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.FeatureInfoDto;
import ru.nsu.sberlab.models.dto.PropertyTypeDto;
import ru.nsu.sberlab.models.entities.Feature;

import java.util.function.Function;

@Service
public class FeatureDtoMapper implements Function<Feature, FeatureInfoDto> {
    @Override
    public FeatureInfoDto apply(Feature feature) {
        return new FeatureInfoDto(
                feature.getDescription(),
                new PropertyTypeDto(feature.getProperty().getPropertyValue())
        );
    }
}
