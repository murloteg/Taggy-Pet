package ru.nsu.sberlab.services;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.exceptions.FailedUserCreationException;
import ru.nsu.sberlab.exceptions.PropertyTypeNotFoundException;
import ru.nsu.sberlab.models.dto.FeatureCreationDto;
import ru.nsu.sberlab.models.dto.PetCreationDto;
import ru.nsu.sberlab.models.dto.PetInfoDto;
import ru.nsu.sberlab.models.dto.UserRegistrationDto;
import ru.nsu.sberlab.models.entities.*;
import ru.nsu.sberlab.models.enums.Property;
import ru.nsu.sberlab.models.enums.Role;
import ru.nsu.sberlab.models.mappers.PetInfoDtoMapper;
import ru.nsu.sberlab.repositories.DeletedUserRepository;
import ru.nsu.sberlab.repositories.FeaturesRepository;
import ru.nsu.sberlab.repositories.PropertiesRepository;
import ru.nsu.sberlab.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final DeletedUserRepository deletedUserRepository;
    private final FeaturesRepository featuresRepository;
    private final PropertiesRepository propertiesRepository;
    private final PetInfoDtoMapper petInfoDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final PropertyResolverUtils propertyResolver;

    @Transactional
    public void createUser(UserRegistrationDto userDto) {
        User user = new User(
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getFirstName(),
                userDto.getPassword()
        );
        Optional<User> currentUser = userRepository.findByEmail(user.getEmail());
        if (currentUser.isPresent() && currentUser.get().isActive()) {
            throw new FailedUserCreationException();
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findUserByUserId(userId).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
        DeletedUser deletedUser = new DeletedUser(
                user.getUserId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstName(),
                user.getDateOfCreated()
        );
        deletedUserRepository.save(deletedUser);
        userRepository.deleteById(user.getUserId());
    }

    @Transactional
    public void createPet(User principal, PetCreationDto petCreationDto) {
        User user = userRepository.findUserByUserId(principal.getUserId()).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
        user.getPets().add(
                new Pet(
                        petCreationDto.getChipId(),
                        petCreationDto.getType(),
                        petCreationDto.getBreed(),
                        petCreationDto.getSex(),
                        petCreationDto.getName(),
                        createFeaturesFromPetCreationDto(principal, petCreationDto.getFeatures())
                )
        );
        userRepository.save(user);
    }

    public List<PetInfoDto> petsListByUserId(Long userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(
                        () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
                )
                .getPets()
                .stream()
                .map(petInfoDtoMapper)
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
    }

    private List<Feature> createFeaturesFromPetCreationDto(User principal, List<FeatureCreationDto> featureCreationDtoList) {
        List<Feature> features = new ArrayList<>();
        for (int i = 0; i < Property.values().length; ++i) {
            if (featureCreationDtoList.get(i).getDescription().isEmpty()) {
                continue;
            }
            Feature feature = new Feature(
                    featureCreationDtoList.get(i).getDescription(),
                    resolvePropertyType(i),
                    principal
            );
            featuresRepository.save(feature);
            features.add(feature);
        }
        return features;
    }

    private PropertyType resolvePropertyType(int ordinal) {
        return propertiesRepository.findById((long) ordinal).orElseThrow(
                () -> new PropertyTypeNotFoundException(message("api.server.error.property-not-found"))
        );
    }

    private String message(String property) {
        return propertyResolver.resolve(property, Locale.getDefault());
    }
}
