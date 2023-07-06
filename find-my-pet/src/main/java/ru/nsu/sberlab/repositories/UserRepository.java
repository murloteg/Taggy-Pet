package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.sberlab.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
