package ru.nsu.sberlab.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.model.dto.SocialNetworkEditDto;
import ru.nsu.sberlab.model.dto.SocialNetworkRegistrationDto;
import ru.nsu.sberlab.model.dto.UserEditDto;
import ru.nsu.sberlab.model.entity.User;
import ru.nsu.sberlab.service.SocialNetworkPropertiesService;
import ru.nsu.sberlab.service.UserSocialNetworkService;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserEditDtoMapper implements Function<User, UserEditDto> {
    private final UserSocialNetworkService userSocialNetworkService;

    @Override
    public UserEditDto apply(User user) {
        return new UserEditDto(
                user.getFirstName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.isHasPermitToShowEmail(),
                user.isHasPermitToShowPhoneNumber(),
                user.getPassword(),
                userSocialNetworkService.getAllSocialNetworksEditDtoByUser(user)
        );
    }
}
