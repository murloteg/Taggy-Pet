package ru.nsu.sberlab.services;

import ru.nsu.sberlab.models.Pet;
import ru.nsu.sberlab.models.User;
import ru.nsu.sberlab.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.repositories.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByPrincipal(Principal principal) {
        List<Pet> pets = getPets();
        List<Pet> availablePetsForUser = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getUser().getEmail().equals(getUserByPrincipal(principal).getEmail())) {
                availablePetsForUser.add(pet);
            }
        }
        return availablePetsForUser;
    }

    public void savePet(Principal principal, Pet pet) {
        pet.setUser(getUserByPrincipal(principal));
        petRepository.save(pet);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return new User();
        }
        return userRepository.findByEmail(principal.getName());
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