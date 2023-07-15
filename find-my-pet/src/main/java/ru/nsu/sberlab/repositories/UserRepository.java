package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findUserByUserId(Long userId);
    @Query(value = "SELECT * FROM pets pet " +
            "JOIN users_pets up ON pet.pet_id = up.pet_id " +
            "WHERE up.user_id = :user_id", nativeQuery = true)
    List<Pet> findAllPetsByUserId(@Param("user_id") Long userId); // FIXME: converting error from Object to Pet
}
