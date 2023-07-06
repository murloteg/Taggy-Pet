package ru.nsu.sberlab.services;

import ru.nsu.sberlab.models.Pet;
import ru.nsu.sberlab.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public List<Pet> getPets(String chipId) {
        if (chipId != null) {
            petRepository.findByChipId(chipId);
        }
        return petRepository.findAll();
    }

    public void saveProduct(Pet pet) {
        petRepository.save(pet);
    }

    public Pet getPetByChipId(String chipId) {
        return petRepository.findByChipId(chipId);
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}
