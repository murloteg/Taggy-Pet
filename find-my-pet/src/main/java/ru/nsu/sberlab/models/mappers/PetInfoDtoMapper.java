package ru.nsu.sberlab.models.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.PetInfoDto;
import ru.nsu.sberlab.models.dto.UserInfoDto;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.User;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PetInfoDtoMapper implements Function<Pet, PetInfoDto> {
    @Override
    public PetInfoDto apply(Pet pet) {
        User user = pet.getUser();
        return new PetInfoDto(
                pet.getChipId(),
                pet.getType(),
                pet.getBreed(),
                pet.getSex(),
                pet.getName(),
                new UserInfoDto(
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getFirstName()
                )
        );
    }
}
