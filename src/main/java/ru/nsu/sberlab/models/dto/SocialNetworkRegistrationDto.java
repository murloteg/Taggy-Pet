package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialNetworkRegistrationDto {
    private Long propertyId;
    private String shortName;
}