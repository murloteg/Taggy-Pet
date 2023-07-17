package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.sberlab.models.entities.Pet;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByChipId(String chipId);
    void deleteByChipId(String chipId);
}
