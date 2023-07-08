package ru.nsu.sberlab.repositories;

import ru.nsu.sberlab.models.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByChipId(String chipId);
}
