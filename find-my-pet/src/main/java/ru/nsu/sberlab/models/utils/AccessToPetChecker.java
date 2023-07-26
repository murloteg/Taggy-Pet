package ru.nsu.sberlab.models.utils;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.repositories.UserRepository;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AccessToPetChecker {
    private final UserRepository userRepository;
    private final PropertyResolverUtils propertyResolver;

    public boolean checkIfUserHasAccess(User principal, Pet pet) {
        User user = userRepository.findByEmail(principal.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
        return user.getPets()
                .stream()
                .anyMatch(i -> i.getChipId().equals(pet.getChipId()));
    }

    private String message(String property) {
        return propertyResolver.resolve(property, Locale.getDefault());
    }
}
