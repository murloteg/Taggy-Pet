package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetDto {
    private final Long id;
    private final String chipId;
    private final String type;
    private final String breed;
    private final String sex;
    private final String name;
    private final UserDto user;
}
