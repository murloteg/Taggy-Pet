package ru.nsu.sberlab.models.mappers;

import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.dto.SocialNetworkInfoDto;
import ru.nsu.sberlab.models.dto.UserInfoDto;
import ru.nsu.sberlab.models.entities.User;

import java.util.function.Function;

@Service
public class UserInfoMapper implements Function<User, UserInfoDto> {
    @Override
    public UserInfoDto apply(User user) {
        String handledEmail = null;
        if (user.isHasPermitToShowEmail()) {
            handledEmail = user.getEmail();
        }
        String handledPhoneNumber = null;
        if (user.isHasPermitToShowPhoneNumber()) {
            handledPhoneNumber = user.getPhoneNumber();
        }
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
