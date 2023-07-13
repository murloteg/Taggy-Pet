package ru.nsu.sberlab.models.mappers;

import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.UserInfoDto;
import ru.nsu.sberlab.models.entities.User;

import java.util.function.Function;

@Service
public class UserInfoMapper implements Function<User, UserInfoDto> {
    @Override
    public UserInfoDto apply(User user) {
        return new UserInfoDto(
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstName()
        );
    }
}
