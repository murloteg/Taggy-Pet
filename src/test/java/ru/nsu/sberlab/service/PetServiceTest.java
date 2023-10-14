package ru.nsu.sberlab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import ru.nsu.sberlab.dao.FeaturePropertiesRepository;
import ru.nsu.sberlab.dao.PetRepository;
import ru.nsu.sberlab.dao.UserRepository;
import ru.nsu.sberlab.model.dto.PetEditDto;
import ru.nsu.sberlab.model.dto.PetInfoDto;
import ru.nsu.sberlab.model.entity.Pet;
import ru.nsu.sberlab.model.entity.User;
import ru.nsu.sberlab.model.enums.Role;
import ru.nsu.sberlab.model.enums.Sex;
import ru.nsu.sberlab.model.mapper.PetEditDtoMapper;
import ru.nsu.sberlab.model.mapper.PetInfoDtoMapper;
import ru.nsu.sberlab.model.util.FeaturesConverter;
import ru.nsu.sberlab.model.util.PetCleaner;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {
    private PetService petService;
    @Mock
    private PetRepository petRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FeaturePropertiesRepository featurePropertiesRepository;
    @Mock
    private PetInfoDtoMapper petInfoDtoMapper;
    @Mock
    private PetEditDtoMapper petEditDtoMapper;
    @Mock
    private FeaturesConverter featuresConverter;
    @Mock
    private PetCleaner petCleaner;
    @Mock
    private PropertyResolverUtils propertyResolver;

    @BeforeEach
    void setUp() {
        petService = new PetService(
                petRepository,
                userRepository,
                featurePropertiesRepository,
                petInfoDtoMapper,
                petEditDtoMapper,
                featuresConverter,
                petCleaner,
                propertyResolver
        );
    }

    @Test
    void itShouldReturnPetInfoByChipId() {
        String chipId = "999999999999999";
        String stampId = "TTT 1111";
        String type = "Cat";
        String breed = "Unknown";
        Sex sex = Sex.FEMALE;
        String name = "Pancake";
        Pet pet = new Pet(chipId, stampId, type, breed, sex, name, Collections.emptyList(), null);
        PetInfoDto expectedDto = new PetInfoDto(chipId, stampId, name, type, breed, sex, null, Collections.emptyList(), null);

        given(petRepository.findByChipId(chipId))
                .willReturn(Optional.of(pet));

        given(petInfoDtoMapper.apply(pet))
                .willReturn(expectedDto);

        ArgumentCaptor<Pet> petArgumentCaptor = ArgumentCaptor.forClass(Pet.class);
        PetInfoDto result = petService.getPetInfoBySearchParameter(chipId);

        verify(petRepository).findByChipId(chipId);
        verify(petInfoDtoMapper).apply(petArgumentCaptor.capture());
        Pet capturedPet = petArgumentCaptor.getValue();
        assertThat(capturedPet).isEqualTo(pet);
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void itShouldReturnPetInfoByStampId() {
        String chipId = "999999999999999";
        String stampId = "TTT 1111";
        String type = "Cat";
        String breed = "Unknown";
        Sex sex = Sex.FEMALE;
        String name = "Pancake";
        Pet pet = new Pet(chipId, stampId, type, breed, sex, name, Collections.emptyList(), null);
        PetInfoDto expectedDto = new PetInfoDto(chipId, stampId, name, type, breed, sex, null, Collections.emptyList(), null);

        given(petRepository.findByStampId(stampId))
                .willReturn(Optional.of(pet));

        given(petInfoDtoMapper.apply(pet))
                .willReturn(expectedDto);

        ArgumentCaptor<Pet> petArgumentCaptor = ArgumentCaptor.forClass(Pet.class);
        PetInfoDto result = petService.getPetInfoBySearchParameter(stampId);

        verify(petRepository).findByStampId(stampId);
        verify(petInfoDtoMapper).apply(petArgumentCaptor.capture());
        Pet capturedPet = petArgumentCaptor.getValue();
        assertThat(capturedPet).isEqualTo(pet);
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void itShouldReturnPetEditDtoByChipId() {
        String chipId = "999999999999999";
        String stampId = "TTT 1111";
        String type = "Cat";
        String breed = "Unknown";
        Sex sex = Sex.FEMALE;
        String name = "Pancake";
        Pet pet = new Pet(chipId, stampId, type, breed, sex, name, Collections.emptyList(), null);
        PetEditDto expectedDto = new PetEditDto(chipId, stampId, name, type, breed, sex, Collections.emptyList(), null, null);

        given(petRepository.findByChipId(chipId))
                .willReturn(Optional.of(pet));

        given(petEditDtoMapper.apply(pet, Collections.emptyList()))
                .willReturn(expectedDto);

        PetEditDto result = petService.getPetEditDtoByChipId(chipId);

        verify(petRepository).findByChipId(chipId);
        verify(petEditDtoMapper).apply(pet, Collections.emptyList());
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void itShouldUpdatePetInfo() {
        String chipId = "999999999999999";
        String stampId = "TTT 1111";
        String type = "Cat";
        String breed = "Unknown";
        Sex sex = Sex.FEMALE;
        String name = "Pancake";
        Pet pet = new Pet(chipId, stampId, type, breed, sex, name, Collections.emptyList(), null);

        given(petRepository.findByChipId(chipId))
                .willReturn(Optional.of(pet));

        String email = "test@test";
        String phoneNumber = "+70001110000";
        String firstName = "John";
        String password = "test";
        User user = new User(email, phoneNumber, firstName, password, true, true);
        user.setActive(true);
        user.setRole(Role.ROLE_USER);
        user.getPets().add(pet);

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(user));

        String newName = "Flay";
        PetEditDto petEditDto = new PetEditDto(
                chipId,
                stampId,
                newName,
                type,
                breed,
                sex,
                Collections.emptyList(),
                null,
                new MockMultipartFile("test", new byte[0])
        );
        petService.updatePetInfo(petEditDto, user);

        verify(petRepository).findByChipId(chipId);
        verify(petRepository).save(pet);
        assertThat(pet.getName()).isEqualTo(newName);
    }

    @Test
    void itShouldDeletePet() {
        String chipId = "999999999999999";
        String stampId = "TTT 1111";
        String type = "Cat";
        String breed = "Unknown";
        Sex sex = Sex.FEMALE;
        String name = "Pancake";
        Pet pet = new Pet(chipId, stampId, type, breed, sex, name, Collections.emptyList(), null);

        String email = "test@test";
        String phoneNumber = "+70001110000";
        String firstName = "John";
        String password = "test";
        User user = new User(email, phoneNumber, firstName, password, true, true);
        user.setActive(true);
        user.setRole(Role.ROLE_USER);
        user.getPets().add(pet);

        given(petRepository.findByChipId(chipId))
                .willReturn(Optional.of(pet));

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(user));

        petService.deletePet(chipId, user);

        verify(petRepository).findByChipId(chipId);
        verify(userRepository).findByEmail(email);
        verify(petCleaner).removePet(pet);
        assertThat(user.getPets()).doesNotContain(pet);
    }

    @Test
    void itShouldReturnPetsList() {
        Pageable pageable = PageRequest.of(0, 10);

        given(petRepository.findAll(pageable))
                .willReturn(Page.empty());

        petService.petsList(pageable);

        verify(petRepository).findAll(pageable);
    }
}
