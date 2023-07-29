package ru.nsu.sberlab.services;

import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.exceptions.IllegalAccessToPetException;
import ru.nsu.sberlab.exceptions.InternalServerErrorException;
import ru.nsu.sberlab.exceptions.PetNotFoundException;
import ru.nsu.sberlab.models.dto.PetEditDto;
import ru.nsu.sberlab.exceptions.FailedPetSearchException;
import ru.nsu.sberlab.models.dto.PetInfoDto;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.PetImage;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.mappers.PetEditDtoMapper;
import ru.nsu.sberlab.models.mappers.PetInfoDtoMapper;
import ru.nsu.sberlab.models.utils.FeaturesConverter;
import ru.nsu.sberlab.models.utils.PetCleaner;
import ru.nsu.sberlab.models.utils.PetImagesSaver;
import ru.nsu.sberlab.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.repositories.FeaturePropertiesRepository;
import ru.nsu.sberlab.repositories.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final FeaturePropertiesRepository featurePropertiesRepository;
    private final PetInfoDtoMapper petInfoDtoMapper;
    private final PetEditDtoMapper petEditDtoMapper;
    private final FeaturesConverter featuresConverter;
    private final PetCleaner petCleaner;
    private final PropertyResolverUtils propertyResolver;

    public PetInfoDto getPetInfoByChipId(String chipId) {
        return petRepository.findByChipId(chipId)
                .map(petInfoDtoMapper)
                .orElseThrow(() -> new FailedPetSearchException(chipId));
    }

    public PetEditDto getPetInitializationDtoByChipId(String chipId) {
        return petRepository.findByChipId(chipId)
                .map(pet -> petEditDtoMapper.apply(pet, featurePropertiesRepository.findAll()))
                .orElseThrow(() -> new PetNotFoundException(message("api.server.error.pet-not-found")));
    }

    /**
     * <p> Update information about user's pet.
     * Note: this method doesn't delete features from features table.
     * </p>
     *
     * @param petEditDto this parameter contains new information about pet
     * @param principal            this parameter present user authentication session
     * @author Kirill Bolotov
     */
    @Transactional
    public void updatePetInfo(PetEditDto petEditDto, User principal) {
        Pet pet = petRepository.findByChipId(petEditDto.getChipId()).orElseThrow(
                () -> new PetNotFoundException(message("api.server.error.pet-not-found"))
        );
        User currentUser = userRepository.findByEmail(principal.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
        checkIfUserHasAccessToPet(currentUser, pet);

        pet.setType(petEditDto.getType());
        pet.setBreed(petEditDto.getBreed());
        pet.setSex(petEditDto.getSex());
        pet.setName(petEditDto.getName());
        Map<Long, Feature> featureMap = pet.getFeatures()
                .stream()
                .collect(Collectors.toMap(
                        feature -> feature.getProperty().getPropertyId(), feature -> feature, (a, b) -> b)
                );
        List<Feature> mergedFeatures = featuresConverter.convertFeatureDtoListToFeatures(
                        petEditDto.getFeatures(),
                        currentUser
                )
                .stream()
                .map(feature -> {
                            Long key = feature.getProperty().getPropertyId();
                            Feature value = featureMap.get(key);
                            if (Objects.nonNull(value)) {
                                value.setDescription(feature.getDescription());
                                value.setDateTime(LocalDate.now());
                                return value;
                            }
                            return feature;
                        }
                )
                .collect(Collectors.toCollection(ArrayList<Feature>::new));
        pet.setFeatures(mergedFeatures);
        try {
            PetImage petImage = pet.getPetImage();
            PetImagesSaver.replaceImageOnFileSystem(petEditDto.getImageFile(), petImage);
            pet.setPetImage(petImage);
        } catch (IOException exception) {
            throw new InternalServerErrorException();
        }
        petRepository.save(pet);
    }

    @Transactional
    public void deletePet(String chipId, User principal) {
        Pet pet = petRepository.findByChipId(chipId).orElseThrow(
                () -> new PetNotFoundException(message("api.server.error.pet-not-found"))
        );
        User currentUser = userRepository.findByEmail(principal.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
        checkIfUserHasAccessToPet(currentUser, pet);

        currentUser.getPets().remove(pet);
        pet.getUsers().remove(currentUser);
        petCleaner.clear(pet);
    }

    public List<PetInfoDto> petsList(Pageable pageable) {
        return petRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(petInfoDtoMapper)
                .toList();
    }

    private void checkIfUserHasAccessToPet(User user, Pet pet) {
        boolean userHasAccess = user.getPets()
                .stream()
                .anyMatch(i -> i.getChipId().equals(pet.getChipId()));
        if (!userHasAccess) {
            throw new IllegalAccessToPetException(message("api.server.error.does-not-have-access-to-pet"));
        }
    }

    private String message(String property) {
        return propertyResolver.resolve(property, Locale.getDefault());
    }
}
