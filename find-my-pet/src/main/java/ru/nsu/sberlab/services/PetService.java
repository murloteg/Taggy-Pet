package ru.nsu.sberlab.services;

import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.exceptions.PetNotFoundException;
import ru.nsu.sberlab.models.dto.PetEditDto;
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
import java.util.List;
import java.util.Locale;

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

    public PetEditDto getPetEditDtoByChipId(String chipId) {
        return petRepository.findByChipId(chipId)
                .map(pet -> petEditDtoMapper.apply(pet, propertiesRepository.findAll()))
                .orElseThrow(() -> new PetNotFoundException(message("api.server.error.pet-not-found")));
    }

    public void updatePetInfo(User principal, PetEditDto petEditDto) { // TODO: this method doesn't delete features from table
        Pet pet = petRepository.findByChipId(petEditDto.getChipId()).orElseThrow(
                () -> new PetNotFoundException(message("api.server.error.pet-not-found"))
        );
        pet.setType(petEditDto.getType());
        pet.setBreed(petEditDto.getBreed());
        pet.setSex(petEditDto.getSex());
        pet.setName(petEditDto.getName());
        int propertiesNumber = propertiesRepository.findAll().size();
        boolean[] oldFeaturesFlags = new boolean[propertiesNumber];
        List<Feature> oldFeatures = pet.getFeatures();
        for (Feature oldFeature : oldFeatures) {
            oldFeaturesFlags[oldFeature.getProperty().getPropertyId().intValue()] = true;
        }
        boolean[] newFeaturesFlags = new boolean[propertiesNumber];
        List<Feature> newFeatures = featuresConverter.convertFeaturesFromPetCreationDto(principal, petEditDto.getFeatures());
        for (Feature newFeature : newFeatures) {
            newFeaturesFlags[newFeature.getProperty().getPropertyId().intValue()] = true;
        }

        int oldFeaturesIndex = 0;
        for (int i = 0; i < propertiesNumber; ++i) {
            if (oldFeaturesFlags[i] && newFeaturesFlags[i]) {
                oldFeatures.get(oldFeaturesIndex).setDescription(newFeatures.get(oldFeaturesIndex).getDescription());
                oldFeatures.get(oldFeaturesIndex).setDateTime(LocalDate.now());
                ++oldFeaturesIndex;
            } else if (oldFeaturesFlags[i] && !newFeaturesFlags[i]) {
                oldFeatures.remove(oldFeaturesIndex);
                oldFeaturesIndex = Math.max(oldFeaturesIndex - 1, 0);
            } else if (!oldFeaturesFlags[i] && newFeaturesFlags[i]) {
                oldFeatures.add(oldFeaturesIndex, newFeatures.get(oldFeaturesIndex));
                ++oldFeaturesIndex;
            }
        }
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
