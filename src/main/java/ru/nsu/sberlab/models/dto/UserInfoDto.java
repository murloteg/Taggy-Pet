package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private final String email;
    private final String phoneNumber;
    private final String firstName;
    private final List<SocialNetworkInfoDto> socialNetworks;
}
