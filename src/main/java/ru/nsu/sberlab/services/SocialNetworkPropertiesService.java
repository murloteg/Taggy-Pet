package ru.nsu.sberlab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.entities.SocialNetwork;
import ru.nsu.sberlab.repositories.SocialNetworkPropertiesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialNetworkPropertiesService {
    private final SocialNetworkPropertiesRepository socialNetworkPropertiesRepository;

    public List<SocialNetwork> properties() {
        return socialNetworkPropertiesRepository.findAll();
    }
}
