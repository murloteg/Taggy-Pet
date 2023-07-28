package ru.nsu.sberlab.models.mappers;

import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.FeatureCreationDto;
import ru.nsu.sberlab.models.entities.Feature;

import java.util.function.Function;

@Service
public class FeatureCreationDtoMapper implements Function<Feature, FeatureCreationDto> {
    @Override
    public FeatureCreationDto apply(Feature feature) {
        return new FeatureCreationDto(feature.getDescription(), feature.getProperty().getPropertyId());
    }
}
