package ru.nsu.sberlab.model.mapper;

import org.springframework.stereotype.Service;
import ru.nsu.sberlab.model.dto.SocialNetworkInfoDto;
import ru.nsu.sberlab.model.dto.UserInfoDto;
import ru.nsu.sberlab.model.entity.User;

import java.util.function.Function;

@Service
public class UserInfoMapper implements Function<User, UserInfoDto> {
    @Override
    public UserInfoDto apply(User user) {
        String handledEmail = user.isHasPermitToShowEmail() ? user.getEmail() : null;
        String handledPhoneNumber = user.isHasPermitToShowPhoneNumber() ? user.getPhoneNumber() : null;
        return new UserInfoDto(
                handledEmail,
                handledPhoneNumber,
                user.getFirstName(),
                user.getUserSocialNetworks()
                        .stream()
                        .map(socialNetwork -> new SocialNetworkInfoDto(
                                        socialNetwork.getSocialNetwork().getPropertyValue(),
                                        socialNetwork.getSocialNetwork().getBaseUrl(),
                                        socialNetwork.getShortName()
                                )
                        )
                        .toList()
        );
    }
}
