package ru.nsu.sberlab.models.utils;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.exceptions.PropertyNotFoundException;
import ru.nsu.sberlab.models.dto.FeatureCreationDto;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.repositories.FeaturePropertiesRepository;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class FeaturesConverter {
    private final FeaturePropertiesRepository featurePropertiesRepository;
    private final PropertyResolverUtils propertyResolver;

    public List<Feature> convertFeatureDtoListToFeatures(List<FeatureCreationDto> featureCreationDtoList, User user) {
        return featureCreationDtoList
                .stream()
                .filter(dto -> !dto.getDescription().isEmpty())
                .map(dto -> new Feature(
                                dto.getDescription(),
                                featurePropertiesRepository.findById(dto.getPropertyId()).orElseThrow(
                                        () -> new PropertyNotFoundException(message("api.server.error.property-not-found"))),
                                user
                        )
                )
                .toList();
    }

    private String message(String property) {
        return propertyResolver.resolve(property, Locale.getDefault());
    }
}
