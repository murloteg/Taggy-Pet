package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nsu.sberlab.models.enums.Role;

import java.util.Set;

@Getter
@AllArgsConstructor
public class UserDto {
    private final Long id;
    private final String email;
    private final String phoneNumber;
    private final String username;
    private final Set<Role> roles;
}
