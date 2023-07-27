package ru.nsu.sberlab.models.utils;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.exceptions.PropertyNotFoundException;
import ru.nsu.sberlab.models.dto.SocialNetworkRegistrationDto;
import ru.nsu.sberlab.models.entities.SocialNetwork;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.repositories.SocialNetworkPropertiesRepository;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SocialNetworksConverter {
    private final SocialNetworkPropertiesRepository socialNetworkPropertiesRepository;
    private final PropertyResolverUtils propertyResolver;

    public List<SocialNetwork> convertSocialNetworksDtoToSocialNetworks(List<SocialNetworkRegistrationDto> socialNetworkRegistrationDtoList, User principal) {
        return socialNetworkRegistrationDtoList
                .stream()
                .map(dto -> new SocialNetwork(
                                dto.getLink(),
                                socialNetworkPropertiesRepository.findById(dto.getPropertyId()).orElseThrow(
                                        () -> new PropertyNotFoundException(message("api.server.error.property-not-found"))
                                ),
                                principal
                        )
                )
                .toList();
    }

    private String message(String property) {
        return propertyResolver.resolve(property, Locale.getDefault());
    }
}
