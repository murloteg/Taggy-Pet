package ru.nsu.sberlab.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.models.dto.PetDto;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.mappers.PetDtoMapper;
import ru.nsu.sberlab.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final PetDtoMapper petDtoMapper;

    public List<PetDto> petsList(Pageable pageable) {
        return petRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(petDtoMapper)
                .toList();
    }

    public List<PetDto> petsListByUserId(Long userId) {
        return petRepository.findAllByUserId(userId)
                .stream()
                .map(petDtoMapper)
                .toList();
    }

    @Transactional
    public void createPet(User principal, PetDto petDto) {
        Pet pet = petDtoMapper.mapDtoToPet(petDto);
        pet.setUser(principal);
        petRepository.save(pet);
    }

    public PetDto getPetByChipId(String chipId) {
        return petRepository.findByChipId(chipId)
                .map(petDtoMapper)
                .orElse(null);
    }

    public void deletePet(Long id) { // TODO: add this feature later
        petRepository.deleteById(id);
    }
}