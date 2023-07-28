package ru.nsu.sberlab.models.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.PetInitializationDto;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.FeatureProperty;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PetEditDtoMapper implements BiFunction<Pet, List<FeatureProperty>, PetInitializationDto> {
    private final FeatureCreationDtoMapper featureCreationDtoMapper;

    @Override
    public PetInitializationDto apply(Pet pet, List<FeatureProperty> properties) {
        List<Feature> features = pet.getFeatures();
        Map<Long, String> propertyMap = features
                .stream()
                .collect(Collectors.toMap(
                        feature -> feature.getProperty().getPropertyId(), Feature::getDescription, (a, b) -> b)
                );
        IntStream.range(0, properties.size())
                .filter(index -> !propertyMap.containsKey((long) index))
                .forEach(index -> features.add(index, new Feature(properties.get(index))));
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
