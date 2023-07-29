package ru.nsu.sberlab.models.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.PetImageDto;
import ru.nsu.sberlab.models.dto.PetInfoDto;
import ru.nsu.sberlab.models.entities.Pet;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PetInfoDtoMapper implements Function<Pet, PetInfoDto> {
    private final UserInfoMapper userInfoMapper;
    private final FeatureInfoDtoMapper featureInfoDtoMapper;

    @Override
    public PetInfoDto apply(Pet pet) {
        PetImageDto petImageDto = Objects.isNull(pet.getPetImage()) ? null : new PetImageDto(
                pet.getPetImage().getImagePath(),
                pet.getPetImage().getImageUUIDName()
        );
        return new PetInfoDto(
                pet.getChipId(),
                pet.getName(),
                pet.getType(),
                pet.getBreed(),
                pet.getSex(),
                pet.getUsers()
                        .stream()
                        .map(userInfoMapper)
                        .toList(),
                pet.getFeatures()
                        .stream()
                        .map(featureInfoDtoMapper)
                        .sorted(Comparator.naturalOrder())
                        .toList(),
                petImageDto
        );
    }
}
