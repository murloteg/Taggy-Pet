package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationDto {
    private final String email;
    private final String phoneNumber;
    private final String firstName;
    private final String password;
    private final boolean hasPermitToShowPhoneNumber;
    private final boolean hasPermitToShowEmail;
}
