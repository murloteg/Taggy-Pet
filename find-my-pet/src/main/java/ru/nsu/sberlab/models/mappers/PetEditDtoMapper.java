package ru.nsu.sberlab.models.mappers;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.exceptions.PropertyNotFoundException;
import ru.nsu.sberlab.models.dto.PetEditDto;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.repositories.PropertiesRepository;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PetEditDtoMapper implements Function<Pet, PetEditDto> {
    private final FeatureCreationDtoMapper featureCreationDtoMapper;
    private final PropertiesRepository propertiesRepository;
    private final PropertyResolverUtils propertyResolver;

    @Override
    public PetEditDto apply(Pet pet) {
        List<Feature> featureList = pet.getFeatures();
        boolean[] arrayOfContainedProperties = new boolean[propertiesRepository.findAll().size()];
        for (Feature feature : featureList) {
            arrayOfContainedProperties[feature.getProperty().getPropertyId().intValue()] = true;
        }
        for (int i = 0; i < arrayOfContainedProperties.length; ++i) {
            if (!arrayOfContainedProperties[i]) {
                featureList.add(i, new Feature(
                                null,
                                propertiesRepository.findById((long) i).orElseThrow(
                                        () -> new PropertyNotFoundException(message("api.server.error.property-not-found")))
                        )
                );
            }
        }
        return new PetEditDto(
                pet.getChipId(),
                pet.getName(),
                pet.getType(),
                pet.getBreed(),
                pet.getSex(),
                featureList
                        .stream()
                        .map(featureCreationDtoMapper)
                        .toList()
        );
    }

    private String message(String property) {
        return propertyResolver.resolve(property, Locale.getDefault());
    }
}
