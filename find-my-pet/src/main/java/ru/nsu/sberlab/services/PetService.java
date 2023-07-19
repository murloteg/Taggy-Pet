package ru.nsu.sberlab.services;

import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.exceptions.PetNotFoundException;
import ru.nsu.sberlab.models.dto.PetEditDto;
import ru.nsu.sberlab.models.dto.PetInfoDto;
import ru.nsu.sberlab.models.entities.Feature;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.PropertyType;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.mappers.PetEditDtoMapper;
import ru.nsu.sberlab.models.mappers.PetInfoDtoMapper;
import ru.nsu.sberlab.models.utils.FeaturesConverter;
import ru.nsu.sberlab.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
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
                .orElse(null);
    }

    public PetEditDto getPetEditByChipId(String chipId) {
        return petRepository.findByChipId(chipId)
                .map(petEditDtoMapper)
                .orElse(null);
    }

    public void updatePetInfo(User principal, PetEditDto petEditDto) {
        Pet pet = petRepository.findByChipId(petEditDto.getChipId()).orElseThrow(
                () -> new PetNotFoundException(message("api.server.error.pet-not-found"))
        );
        pet.setType(petEditDto.getType());
        pet.setBreed(petEditDto.getBreed());
        pet.setSex(petEditDto.getSex());
        pet.setName(petEditDto.getName());
        pet.setFeatures(featuresConverter.convertFeaturesFromPetCreationDto(principal, petEditDto.getFeatures()));
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
