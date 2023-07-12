package ru.nsu.sberlab.models.mappers;

import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.UserRegistrationDto;
import ru.nsu.sberlab.models.entities.User;

import java.util.function.Function;

@Service
public class UserRegistrationDtoMapper implements Function<User, UserRegistrationDto> {
    @Override
    public UserRegistrationDto apply(User user) {
        return new UserRegistrationDto(
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstName(),
                user.getPassword()
        );
    }
}
