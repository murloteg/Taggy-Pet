package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.sberlab.models.entities.PetImage;

import java.util.Optional;

@Repository
public interface PetImageRepository extends JpaRepository<PetImage, Long> {
    Optional<PetImage> findByImageUUIDName(String uuidName);
}
