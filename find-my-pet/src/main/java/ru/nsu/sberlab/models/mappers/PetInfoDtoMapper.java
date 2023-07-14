package ru.nsu.sberlab.models.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.PetInfoDto;
import ru.nsu.sberlab.models.entities.Pet;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PetInfoDtoMapper implements Function<Pet, PetInfoDto> {
    private final UserInfoMapper userInfoMapper;

    @Override
    public PetInfoDto apply(Pet pet) {
        return new PetInfoDto(
                pet.getChipId(),
                pet.getType(),
                pet.getBreed(),
                pet.getSex(),
                pet.getName(),
                pet.getUsers()
                        .stream()
                        .map(userInfoMapper)
                        .toList()
        );
    }
}
