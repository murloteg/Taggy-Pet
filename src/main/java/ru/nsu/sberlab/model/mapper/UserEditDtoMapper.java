package ru.nsu.sberlab.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.model.dto.SocialNetworkEditDto;
import ru.nsu.sberlab.model.dto.SocialNetworkRegistrationDto;
import ru.nsu.sberlab.model.dto.UserEditDto;
import ru.nsu.sberlab.model.entity.User;
import ru.nsu.sberlab.service.SocialNetworkPropertiesService;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserEditDtoMapper implements Function<User, UserEditDto> {
    private final SocialNetworkPropertiesService socialNetworkPropertiesService;

    @Override
    public UserEditDto apply(User user) {
        return new UserEditDto(
                user.getFirstName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.isHasPermitToShowEmail(),
                user.isHasPermitToShowPhoneNumber(),
                user.getPassword(),
                socialNetworkPropertiesService.properties()
                        .stream()
                        .map(socialNetwork -> new SocialNetworkEditDto(
                                socialNetwork.getPropertyId(),
                                socialNetwork.getPropertyValue(),
                                socialNetworkPropertiesService.getLoginByPropertyId(user, socialNetwork.getPropertyId())))
                        .toList()
        );
    }
}
