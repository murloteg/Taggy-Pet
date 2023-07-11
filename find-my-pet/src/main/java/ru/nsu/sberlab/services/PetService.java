package ru.nsu.sberlab.services;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.models.dto.PetCreationDto;
import ru.nsu.sberlab.models.dto.PetInfoDto;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.mappers.PetInfoDtoMapper;
import ru.nsu.sberlab.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final PetInfoDtoMapper petInfoDtoMapper;

    public List<PetInfoDto> petsList(Pageable pageable) {
        return petRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(petInfoDtoMapper)
                .toList();
    }

    public List<PetInfoDto> petsListByUserId(Long userId) {
        return petRepository.findAllByUserId(userId)
                .stream()
                .map(petInfoDtoMapper)
                .toList();
    }

    @Transactional
    public void createPet(User principal, PetCreationDto petCreationDto) {
        Pet pet = new Pet(
                petCreationDto.getChipId(),
                petCreationDto.getType(),
                petCreationDto.getBreed(),
                petCreationDto.getSex(),
                petCreationDto.getName()
        );
        pet.setUser(principal);
        petRepository.save(pet);
    }

    public PetInfoDto getPetByChipId(String chipId) {
        return petRepository.findByChipId(chipId)
                .map(petInfoDtoMapper)
                .orElse(null);
    }

    public void deletePet(Long id) { // TODO: add this feature later, think about delete by chipId
        petRepository.deleteById(id);
    }
}