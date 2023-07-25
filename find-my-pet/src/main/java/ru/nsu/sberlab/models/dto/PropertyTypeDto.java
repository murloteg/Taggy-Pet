package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertyTypeDto {
    private final String propertyValue;
    private final Long propertyId;
}
