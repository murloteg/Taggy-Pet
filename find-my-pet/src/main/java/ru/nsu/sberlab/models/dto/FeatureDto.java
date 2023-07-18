package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FeatureDto {
    private String description;
    private LocalDateTime dateTime;
    private PropertyTypeDto propertyType;
}
