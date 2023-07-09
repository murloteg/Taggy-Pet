package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private final String email;
    private final String phoneNumber;
    private final String alias;
}
