package ru.nsu.sberlab.services;

import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.exceptions.PetNotFoundException;
import ru.nsu.sberlab.models.dto.PetInitializationDto;
import ru.nsu.sberlab.exceptions.FailedPetSearchException;
import ru.nsu.sberlab.models.dto.PetInfoDto;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.mappers.PetEditDtoMapper;
import ru.nsu.sberlab.models.mappers.PetInfoDtoMapper;
import ru.nsu.sberlab.models.utils.FeaturesConverter;
import ru.nsu.sberlab.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.repositories.PropertiesRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final PropertiesRepository propertiesRepository;
    private final PetInfoDtoMapper petInfoDtoMapper;
    private final PetEditDtoMapper petEditDtoMapper;
    private final FeaturesConverter featuresConverter;
    private final PropertyResolverUtils propertyResolver;

    public List<PetInfoDto> petsList(Pageable pageable) {
        return petRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(petInfoDtoMapper)
                .toList();
    }

    public PetInfoDto getPetInfoByChipId(String chipId) {
        return petRepository.findByChipId(chipId)
                .map(petInfoDtoMapper)
                .orElseThrow(() -> new FailedPetSearchException(chipId));
    }

    public PetInitializationDto getPetInitializationDtoByChipId(String chipId) {
        return petRepository.findByChipId(chipId)
                .map(pet -> petEditDtoMapper.apply(pet, propertiesRepository.findAll()))
                .orElseThrow(() -> new PetNotFoundException(message("api.server.error.pet-not-found")));
    }

    @Transactional
    public void updatePetInfo(PetInitializationDto petInitializationDto, User principal) { // TODO: this method doesn't delete features from table
        Pet pet = petRepository.findByChipId(petInitializationDto.getChipId()).orElseThrow(
                () -> new PetNotFoundException(message("api.server.error.pet-not-found"))
        );
        pet.setType(petInitializationDto.getType());
        pet.setBreed(petInitializationDto.getBreed());
        pet.setSex(petInitializationDto.getSex());
        pet.setName(petInitializationDto.getName());
        Map<Long, Feature> featureMap = pet.getFeatures()
                .stream()
                .collect(Collectors.toMap(
                        feature -> feature.getProperty().getPropertyId(), feature -> feature, (a, b) -> b)
                );
        List<Feature> mergedFeatures = featuresConverter.convertFeatureDtoListToFeatures(petInitializationDto.getFeatures(), principal)
                .stream()
                .map(feature -> {
                            Long key = feature.getProperty().getPropertyId();
                            Feature value = featureMap.get(key);
                            if (Objects.nonNull(value)) {
                                feature.setFeatureId(value.getFeatureId());
                                feature.setPets(value.getPets());
                                feature.setDateTime(LocalDate.now());
                            }
                            return feature;
                        }
                )
                .toList();
        pet.setFeatures(mergedFeatures);
        petRepository.save(pet);
    }

    @Transactional
    public void deletePet(String chipId) { // TODO: add this feature later
        petRepository.deleteByChipId(chipId);
    }

    private String message(String property) {
        return propertyResolver.resolve(property, Locale.getDefault());
    }
}
