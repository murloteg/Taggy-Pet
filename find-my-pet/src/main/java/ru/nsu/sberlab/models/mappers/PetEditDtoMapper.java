package ru.nsu.sberlab.models.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.PetEditDto;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.PropertyType;

import java.util.*;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class PetEditDtoMapper implements BiFunction<Pet, List<PropertyType>, PetEditDto> {
    private final FeatureCreationDtoMapper featureCreationDtoMapper;

    @Override
    public PetEditDto apply(Pet pet, List<PropertyType> properties) {
        Map<Long, String> propertyMap = new HashMap<>();
        for (PropertyType property : properties) {
            propertyMap.put(property.getPropertyId(), null);
        }
        List<Feature> features = pet.getFeatures();
        for (Feature feature : features) {
            propertyMap.replace(feature.getProperty().getPropertyId(), feature.getDescription());
        }
        for (int i = 0; i < propertyMap.size(); ++i) {
            if (Objects.isNull(propertyMap.get((long) i))) {
                features.add(i, new Feature(null, properties.get(i)));
            }
        }
        return new PetEditDto(
                pet.getChipId(),
                pet.getName(),
                pet.getType(),
                pet.getBreed(),
                pet.getSex(),
                features
                        .stream()
                        .map(featureCreationDtoMapper)
                        .toList()
        );
    }
}
