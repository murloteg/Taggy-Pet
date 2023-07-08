package ru.nsu.sberlab.services;

import ru.nsu.sberlab.models.dto.PetDto;
import ru.nsu.sberlab.models.dto.UserDto;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.mappers.PetDtoMapper;
import ru.nsu.sberlab.models.mappers.UserDtoMapper;
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
    private final PetDtoMapper petDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public List<PetDto> getPetsByPrincipal(Principal principal) {
        List<PetDto> pets = getPets();
        List<PetDto> availablePetsForUser = new ArrayList<>();
//        User currentUser = getUserByPrincipal(principal);
        // FIXME: use role "Privileged Access" instead flag
        // FIXME: use join operations instead foreach cycle
        availablePetsForUser = pets;
        return availablePetsForUser;
    }

    public void createPet(Principal principal, Pet pet) {
        UserDto userDto = getUserByPrincipal(principal);
        pet.setUser(userDtoMapper.mapDtoToUser(userDto, new User()));
        petRepository.save(pet);
    }

    public UserDto getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return null;
        }
        return userRepository.findByEmail(principal.getName())
                .map(userDtoMapper)
                .orElse(null);
    }

    public PetDto getPetByChipId(String chipId) {
        return petRepository.findByChipId(chipId)
                .map(petDtoMapper)
                .orElse(null);
    }

    public void deletePet(Long id) { // TODO: add this feature
        petRepository.deleteById(id);
    }

    private List<PetDto> getPets() {
        return petRepository.findAll()
                .stream()
                .map(petDtoMapper)
                .toList();
    }
}