package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nsu.sberlab.models.enums.Sex;

@Getter
@AllArgsConstructor
public class PetDto {
    private final Long id;
    private final String chipId;
    private final String type;
    private final String breed;
    private final Sex sex;
    private final String name;
    private final UserInfoDto user;
}
