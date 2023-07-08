package ru.nsu.sberlab.models.mappers;

import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.UserDto;
import ru.nsu.sberlab.models.entities.User;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getUsername(),
                user.getRoles()
        );
    }

    public User mapDtoToUser(UserDto userDto, User user) {
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUsername(userDto.getUsername());
        user.setRoles(userDto.getRoles());
        return user;
    }
}
