package ru.nsu.sberlab.services;

import ru.nsu.sberlab.models.Pet;
import ru.nsu.sberlab.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public void saveProduct(Pet pet) {
        petRepository.save(pet);
    }

    public Pet getPetByChip(Long chip) {
        if (chip != null) petRepository.findByChip(chip);
        return null;
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    public Pet getPetById(Long id) {
        return null;
    }
}
