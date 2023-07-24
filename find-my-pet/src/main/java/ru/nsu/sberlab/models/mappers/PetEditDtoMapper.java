package ru.nsu.sberlab.models.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.PetInitializationDto;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.PropertyType;

import java.util.*;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class PetEditDtoMapper implements BiFunction<Pet, List<PropertyType>, PetInitializationDto> {
    private final FeatureCreationDtoMapper featureCreationDtoMapper;

    @Override
    public PetInitializationDto apply(Pet pet, List<PropertyType> properties) {
        Map<Long, String> propertyMap = new HashMap<>();
        List<Feature> features = pet.getFeatures();
        for (Feature feature : features) {
            propertyMap.put(feature.getProperty().getPropertyId(), feature.getDescription());
        }
        int index = 0;
        while (index < properties.size()) {
            if (!propertyMap.containsKey((long) index)) {
                features.add(index, new Feature(null, properties.get(index)));
            }
            ++index;
        }
        return new PetInitializationDto(
                pet.getChipId(),
                pet.getName(),
                pet.getType(),
                pet.getBreed(),
                pet.getSex(),
                features
                        .stream()
                        .map(featureCreationDtoMapper)
                        .sorted(Comparator.naturalOrder())
                        .toList()
        );
    }
}
