package ru.nsu.sberlab.service;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.nsu.sberlab.exception.FailedUserCreationException;
import ru.nsu.sberlab.exception.AddPetImageException;
import ru.nsu.sberlab.model.dto.*;
import ru.nsu.sberlab.model.entity.DeletedUser;
import ru.nsu.sberlab.model.entity.Pet;
import ru.nsu.sberlab.model.entity.PetImage;
import ru.nsu.sberlab.model.entity.User;
import ru.nsu.sberlab.model.enums.Role;
import ru.nsu.sberlab.model.mapper.PetInfoDtoMapper;
import ru.nsu.sberlab.model.util.FeaturesConverter;
import ru.nsu.sberlab.model.util.PetCleaner;
import ru.nsu.sberlab.model.util.SocialNetworksConverter;
import ru.nsu.sberlab.dao.DeletedUserRepository;
import ru.nsu.sberlab.dao.UserRepository;

import java.io.IOException;
import java.util.*;
import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final DeletedUserRepository deletedUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final SocialNetworksConverter socialNetworksConverter;
    private final FeaturesConverter featuresConverter;
    private final PetInfoDtoMapper petInfoDtoMapper;
    private final PetCleaner petCleaner;
    private final PropertyResolverUtils propertyResolver;

    @Value("${default.pet.image.name}")
    private String defaultPetImageName;

    @Transactional
    public void createUser(UserRegistrationDto userDto) {
        User user = new User(
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getFirstName(),
                userDto.getPassword(),
                userDto.isHasPermitToShowPhoneNumber(),
                userDto.isHasPermitToShowEmail()
        );
        Optional<User> currentUser = userRepository.findByEmail(user.getEmail());
        if (currentUser.isPresent() && currentUser.get().isActive()) {
            throw new FailedUserCreationException(user.getEmail());
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        User savedUser = userRepository.save(user);
        savedUser.setUserSocialNetworks(socialNetworksConverter.convertSocialNetworksDtoToSocialNetworks(
                        userDto.getSocialNetworks(),
                        savedUser
                )
        );
    }

    @Transactional
    public void updateUserInfo(UserEditDto userEditDto) {
        User user = userRepository.findByEmail(userEditDto.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
        user.setFirstName(userEditDto.getFirstName());
        user.setPhoneNumber(userEditDto.getPhoneNumber());
        Optional.of(userEditDto.getPassword()).filter(not(String::isBlank)).ifPresent(
                newPassword -> user.setPassword(passwordEncoder.encode(newPassword))
        );
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

        user.getUserSocialNetworks().clear();
        user.getFeatures().clear();
        List<Pet> pets = user.getPets();
        pets.forEach(pet -> petCleaner.detachUser(pet, user));
        pets.forEach(petCleaner::detachFeatures);
        userRepository.deleteByUserId(user.getUserId());
        pets.forEach(petCleaner::removePet);
    }

    @Transactional
    public void createPet(PetCreationDto petCreationDto, User principal) {
        User user = userRepository.findUserByUserId(principal.getUserId()).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
        PetImage petImage = new PetImage();
        MultipartFile imageFile = petCreationDto.getImageFile();
        if (imageFile.isEmpty()) {
            petImage.setImageUUIDName(defaultPetImageName);
        } else {
            try {
                petImage.setImageData(imageFile.getBytes());
                petImage.setImageUUIDName(UUID.randomUUID() + imageFile.getName());
                petImage.setContentType(imageFile.getContentType());
                petImage.setSize(imageFile.getSize());
            } catch (IOException exception) {
                throw new AddPetImageException(exception.getMessage());
            }
        }
        user.getPets().add(
                new Pet(
                        petCreationDto.getChipId(),
                        petCreationDto.getStampId(),
                        petCreationDto.getType(),
                        petCreationDto.getBreed(),
                        petCreationDto.getSex(),
                        petCreationDto.getName(),
                        featuresConverter.convertFeatureDtoListToFeatures(petCreationDto.getFeatures(), user),
                        petImage
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
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
    }

    private String message(String property) {
        return propertyResolver.resolve(property, Locale.getDefault());
    }
}
