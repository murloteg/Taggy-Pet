package ru.nsu.sberlab.model.mapper;

import org.springframework.stereotype.Service;
import ru.nsu.sberlab.model.dto.SocialNetworkRegistrationDto;
import ru.nsu.sberlab.model.dto.UserEditDto;
import ru.nsu.sberlab.model.entity.User;

import java.util.function.Function;

@Service
public class UserEditDtoMapper implements Function<User, UserEditDto> {
    @Override
    public UserEditDto apply(User user) {
        return new UserEditDto(
                user.getFirstName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.isHasPermitToShowEmail() ? true : null,
                user.isHasPermitToShowPhoneNumber() ? true : null,
                user.getPassword(),
                user.getUserSocialNetworks()
                        .stream()
                        .map(socialNetwork -> new SocialNetworkRegistrationDto(
                                socialNetwork.getSocialNetwork().getPropertyId(),
                                socialNetwork.getShortName()
                        ))
                        .toList()

        );
    }
}
