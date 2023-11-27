package ru.nsu.sberlab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.model.entity.SocialNetwork;
import ru.nsu.sberlab.dao.SocialNetworkPropertiesRepository;
import ru.nsu.sberlab.model.entity.User;
import ru.nsu.sberlab.model.entity.UserSocialNetwork;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialNetworkPropertiesService {
    private final SocialNetworkPropertiesRepository socialNetworkPropertiesRepository;

    public List<SocialNetwork> properties() {
        return socialNetworkPropertiesRepository.findAll();
    }

    public String getLoginByPropertyId(User user, Long propertyId) {
        for (UserSocialNetwork socialNetwork : user.getUserSocialNetworks()) {
            if (socialNetwork.getSocialNetwork().getPropertyId().equals(propertyId)) {
                return socialNetwork.getShortName();
            }
        }
        return "";
    }
}
