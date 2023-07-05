package ru.nsu.sberlab.repositories;

import ru.nsu.sberlab.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Pet findByChip(Long chipId);
}
