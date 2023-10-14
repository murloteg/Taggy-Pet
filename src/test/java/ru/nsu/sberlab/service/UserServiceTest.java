package ru.nsu.sberlab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.nsu.sberlab.dao.DeletedUserRepository;
import ru.nsu.sberlab.dao.UserRepository;
import ru.nsu.sberlab.model.dto.PetCreationDto;
import ru.nsu.sberlab.model.dto.PetInfoDto;
import ru.nsu.sberlab.model.dto.UserInfoDto;
import ru.nsu.sberlab.model.dto.UserRegistrationDto;
import ru.nsu.sberlab.model.entity.DeletedUser;
import ru.nsu.sberlab.model.entity.Pet;
import ru.nsu.sberlab.model.entity.User;
import ru.nsu.sberlab.model.enums.Role;
import ru.nsu.sberlab.model.enums.Sex;
import ru.nsu.sberlab.model.mapper.PetInfoDtoMapper;
import ru.nsu.sberlab.model.util.FeaturesConverter;
import ru.nsu.sberlab.model.util.PetCleaner;
import ru.nsu.sberlab.model.util.SocialNetworksConverter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DeletedUserRepository deletedUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SocialNetworksConverter socialNetworksConverter;
    @Mock
    private FeaturesConverter featuresConverter;
    @Mock
    private PetInfoDtoMapper petInfoDtoMapper;
    @Mock
    private PetCleaner petCleaner;
    @Mock
    private PropertyResolverUtils propertyResolver;

    @BeforeEach
    void setUp() {
        userService = new UserService(
                userRepository,
                deletedUserRepository,
                passwordEncoder,
                socialNetworksConverter,
                featuresConverter,
                petInfoDtoMapper,
                petCleaner,
                propertyResolver
        );
    }

    @Test
    void itShouldCreateUser() {
        String email = "test@test";
        String phoneNumber = "+70001110000";
        String firstName = "John";
        String password = null;
        User user = new User(email, phoneNumber, firstName, password, true, true);
        user.setActive(true);
        user.setRole(Role.ROLE_USER);

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                email,
                phoneNumber,
                firstName,
                password,
                true,
                true,
                null
        );

        given(userRepository.findByEmail(email))
                .willReturn(Optional.empty());

        given(userRepository.save(user))
                .willReturn(user);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        userService.createUser(userRegistrationDto);

        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void itShouldUpdateInfoAboutUser() {
        long id = 1L;
        String email = "test@test";
        String phoneNumber = "+70001110000";
        String firstName = "John";
        String password = "test";
        User user = new User(email, phoneNumber, firstName, password, true, true);
        user.setUserId(id);

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(user));

        String newPhoneNumber = "+79990009999";
        String newFirstName = "William";
        UserInfoDto userInfoDto = new UserInfoDto(email, newPhoneNumber, newFirstName, null);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        userService.updateUserInfo(userInfoDto);

        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser.getPhoneNumber()).isEqualTo(newPhoneNumber);
        assertThat(capturedUser.getFirstName()).isEqualTo(newFirstName);
    }

    @Test
    void itShouldDeleteUser() {
        long id = 1L;
        String email = "test@test";
        String phoneNumber = "+70001110000";
        String firstName = "John";
        String password = "test";
        User user = new User(email, phoneNumber, firstName, password, true, true);
        user.setUserId(id);

        given(userRepository.findUserByUserId(id))
                .willReturn(Optional.of(user));

        ArgumentCaptor<DeletedUser> deletedUserArgumentCaptor = ArgumentCaptor.forClass(DeletedUser.class);
        userService.deleteUser(id);

        verify(deletedUserRepository).save(deletedUserArgumentCaptor.capture());
        DeletedUser deletedUser = deletedUserArgumentCaptor.getValue();
        assertThat(deletedUser.getEmail()).isEqualTo(user.getEmail());
        verify(userRepository).deleteByUserId(id);
    }

    @Test
    void itShouldCreatePet() {
        long id = 1L;
        String email = "test@test";
        String phoneNumber = "+70001110000";
        String firstName = "John";
        String password = "test";
        User user = new User(email, phoneNumber, firstName, password, true, true);
        user.setUserId(id);

        PetCreationDto petCreationDto = new PetCreationDto(
                "999999999999999",
                "TTT 1111",
                "Pancake",
                "Cat",
                "Unknown",
                Sex.FEMALE,
                null,
                new MockMultipartFile("test", new byte[0])
        );

        given(userRepository.findUserByUserId(id))
                .willReturn(Optional.of(user));

        assertThat(user.getPets()).isEmpty();
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        userService.createPet(petCreationDto, user);

        verify(userRepository).findUserByUserId(id);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
        assertThat(user.getPets()).hasSize(1);
    }

    @Test
    void itShouldReturnPetsListByUserId() {
        long id = 1L;
        String email = "test@test";
        String phoneNumber = "+70001110000";
        String firstName = "John";
        String password = "test";
        User user = new User(email, phoneNumber, firstName, password, true, true);

        String chipId = "999999999999999";
        String stampId = "TTT 1111";
        String type = "Cat";
        String breed = "Unknown";
        Sex sex = Sex.FEMALE;
        String name = "Pancake";
        Pet pet = new Pet(chipId, stampId, type, breed, sex, name, Collections.emptyList(), null);
        user.setPets(List.of(pet));

        given(userRepository.findUserByUserId(id))
                .willReturn(Optional.of(user));

        given(petInfoDtoMapper.apply(pet))
                .willReturn(
                        new PetInfoDto(
                                pet.getChipId(),
                                pet.getStampId(),
                                pet.getName(),
                                pet.getType(),
                                pet.getBreed(),
                                pet.getSex(),
                                List.of(new UserInfoDto(user.getEmail(), user.getPhoneNumber(), user.getFirstName(), null)),
                                Collections.emptyList(),
                                null
                        )
                );

        ArgumentCaptor<Pet> petArgumentCaptor = ArgumentCaptor.forClass(Pet.class);
        List<PetInfoDto> petsList = userService.petsListByUserId(id);

        verify(userRepository).findUserByUserId(id);
        verify(petInfoDtoMapper).apply(petArgumentCaptor.capture());
        Pet capturedPet = petArgumentCaptor.getValue();
        assertThat(capturedPet).isEqualTo(pet);
        assertThat(petsList).hasSize(1);
    }
}
