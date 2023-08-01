package ru.nsu.sberlab.models.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.repositories.PetRepository;

@Service
@RequiredArgsConstructor
public class PetCleaner {
    private final PetRepository petRepository;

    /**
     * <p>This method detach user from pet's list of users.</p>
     *
     * @param pet  this parameter present pet entity
     * @param user this parameter present user entity
     */
    public void detachUser(Pet pet, User user) {
        pet.getUsers().remove(user);
    }

    /**
     * <p>This method detach all pet's features from pet.
     * Pet's features are removed when the user (owner of features) is deleted.
     * </p>
     *
     * @param pet this parameter present pet entity
     */
    public void detachFeatures(Pet pet) {
        if (pet.getUsers().isEmpty()) {
            pet.getFeatures().forEach(feature -> feature.getPets().remove(pet));
            pet.getFeatures().clear();
        }
    }

    /**
     * <p>This method delete pet from database and delete pet's image from file system.</p>
     *
     * @param pet this parameter present pet entity
     */
    @Transactional
    public void removePet(Pet pet) {
        if (pet.getUsers().isEmpty()) {
//            try {
//                petImageRepository.removePetImage(pet.getPetImage());
//            } catch (IOException exception) {
//                throw new FileSystemErrorException(exception.getMessage());
//            }
            petRepository.deleteByChipId(pet.getChipId());
        }
    }
}
