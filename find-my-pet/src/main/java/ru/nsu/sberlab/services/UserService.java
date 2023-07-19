package ru.nsu.sberlab.services;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.exceptions.FailedUserCreationException;
import ru.nsu.sberlab.models.dto.*;
import ru.nsu.sberlab.models.entities.*;
import ru.nsu.sberlab.models.enums.Role;
import ru.nsu.sberlab.models.mappers.PetInfoDtoMapper;
import ru.nsu.sberlab.models.utils.FeaturesConverter;
import ru.nsu.sberlab.repositories.DeletedUserRepository;
import ru.nsu.sberlab.repositories.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final DeletedUserRepository deletedUserRepository;
    private final FeaturesConverter featuresConverter;
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
                        featuresConverter.convertFeaturesFromPetCreationDto(principal, petCreationDto.getFeatures())
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

    public void updateUserInfo(UserInfoDto userInfoDto) {
        User user = userRepository.findByEmail(userInfoDto.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
        user.setPhoneNumber(userInfoDto.getPhoneNumber());
        user.setFirstName(userInfoDto.getFirstName());
        userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
    }

    private String message(String property) {
        return propertyResolver.resolve(property, Locale.getDefault());
    }
}
