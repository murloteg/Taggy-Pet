package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocialNetworkInfoDto {
    private final String name;
    private final String baseUrl;
    private final String shortName;
}
