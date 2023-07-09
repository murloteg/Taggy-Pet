package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegistrationDto {
    private final String email;
    private final String phoneNumber;
    private final String alias;
    private final String password;
}
