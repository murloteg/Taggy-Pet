package ru.nsu.sberlab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.dao.SocialNetworkPropertiesRepository;
import ru.nsu.sberlab.model.dto.SocialNetworkEditDto;
import ru.nsu.sberlab.model.entity.User;
import ru.nsu.sberlab.model.entity.UserSocialNetwork;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSocialNetworkService {
    private final SocialNetworkPropertiesRepository socialNetworkPropertiesRepository;

    public List<SocialNetworkEditDto> getAllSocialNetworksEditDtoByUser(User user) {
        return socialNetworkPropertiesRepository.findAll()
                .stream()
                .map(socialNetwork -> new SocialNetworkEditDto(
                        socialNetwork.getPropertyId(),
                        socialNetwork.getPropertyValue(),
                        getLoginByPropertyId(user, socialNetwork.getPropertyId())))
                .toList();
    }


    private String getLoginByPropertyId(User user, Long propertyId) {
        for (UserSocialNetwork socialNetwork : user.getUserSocialNetworks()) {
            if (socialNetwork.getSocialNetwork().getPropertyId().equals(propertyId)) {
                return socialNetwork.getShortName();
            }
        }
        return "";
    }
}
