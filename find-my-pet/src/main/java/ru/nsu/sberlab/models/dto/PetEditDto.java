package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nsu.sberlab.models.enums.Sex;

import java.util.List;

@Getter
@AllArgsConstructor
public class PetEditDto {
    private final String chipId;
    private final String name;
    private final String type;
    private final String breed;
    private final Sex sex;
    private final List<FeatureCreationDto> features;
}
