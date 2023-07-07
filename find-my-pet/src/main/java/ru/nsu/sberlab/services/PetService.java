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

//@Service
//@RequiredArgsConstructor
//public class PetService {
//    private List<Pet> pets = new ArrayList<>();
//    private Long id = 0L;
//
//    {
//            pets.add(new Pet(++id, 12341234134134L, "Собака", "Такса", "Сука", "Майра", "2015-11-13"));
//            pets.add(new Pet(++id, 64341234134134L, "Собака", "Хаски", "Самец", "Руди", "2013-11-13"));
//    }
//
//    public List<Pet> getAllPets() {
//        return pets;
//    }
//
//    public void saveProduct(Pet pet) {
//        pet.setId(++id);
//        pets.add(pet);
//    }
//
//    public void deletePet(Long id) {
//        pets.removeIf(pet -> pet.getId().equals(id));
//    }
//
//    public Pet getPetById(Long Id) {
//        for (Pet pet : pets) {
//            if (pet.getId().equals(id)) return pet;
//        }
//        return null;
//    }
//}

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public List<Pet> getPets(String chipId) {
        if (chipId != null) petRepository.findByChipId(chipId);
        return petRepository.findAll();
    }

    public void savePet(Principal principal, Pet pet) {
        pet.setUser(getUserByPrincipal(principal));
        petRepository.save(pet);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public Pet getPetByChipId(String chipId) {
        if (chipId != null) return petRepository.findByChipId(chipId);
        return null;
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

}
