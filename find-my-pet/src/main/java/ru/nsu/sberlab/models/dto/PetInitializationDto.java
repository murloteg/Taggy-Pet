package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.sberlab.models.enums.Sex;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetInitializationDto {
    private String chipId;
    private String name;
    private String type;
    private String breed;
    private Sex sex;
    private List<FeatureCreationDto> features = new ArrayList<>();
}
