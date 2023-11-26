package ru.nsu.sberlab.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserEditDto {
    private final String firstName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final List<SocialNetworkInfoDto> socialNetworks;
}
