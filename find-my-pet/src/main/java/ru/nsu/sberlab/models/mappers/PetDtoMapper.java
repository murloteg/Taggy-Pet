package ru.nsu.sberlab.models.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.PetDto;
import ru.nsu.sberlab.models.dto.UserInfoDto;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.User;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PetDtoMapper implements Function<Pet, PetDto> {
    @Override
    public PetDto apply(Pet pet) {
        User user = pet.getUser();
        return new PetDto(
                pet.getId(),
                pet.getChipId(),
                pet.getType(),
                pet.getBreed(),
                pet.getSex(),
                pet.getName(),
                new UserInfoDto(user.getEmail(), user.getPhoneNumber(), user.getFirstName())
        );
    }

    public Pet mapDtoToPet(PetDto petDto) {
        return new Pet(petDto.getId(), petDto.getChipId(), petDto.getType(), petDto.getBreed(), petDto.getSex(), petDto.getName());
    }
}
