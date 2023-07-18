package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeatureInfoDto {
    private String description;
    private PropertyTypeDto propertyType;
}
