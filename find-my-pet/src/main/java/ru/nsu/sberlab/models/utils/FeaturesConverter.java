package ru.nsu.sberlab.models.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.FeatureCreationDto;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.models.entities.PropertyType;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.repositories.PropertiesRepository;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class FeaturesConverter {
    private final PropertiesRepository propertiesRepository;

    public List<Feature> convertFeaturesFromPetCreationDto(User principal, List<FeatureCreationDto> featureCreationDtoList) {
        List<PropertyType> properties = propertiesRepository.findAll();
        return IntStream.range(0, properties.size())
                .filter(i -> !featureCreationDtoList.get(i).getDescription().isEmpty())
                .mapToObj(
                        i -> new Feature(
                                featureCreationDtoList.get(i).getDescription(),
                                properties.get(i),
                                principal
                        )
                )
                .toList();
    }
}
