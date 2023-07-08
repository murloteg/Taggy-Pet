package ru.nsu.sberlab.models.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.PetDto;
import ru.nsu.sberlab.models.entities.Pet;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PetDtoMapper implements Function<Pet, PetDto> {
    private final UserDtoMapper userDtoMapper;

    @Override
    public PetDto apply(Pet pet) {
        return new PetDto(
                pet.getId(),
                pet.getChipId(),
                pet.getType(),
                pet.getBreed(),
                pet.getSex(),
                pet.getName(),
                userDtoMapper.apply(pet.getUser())
        );
    }
}
