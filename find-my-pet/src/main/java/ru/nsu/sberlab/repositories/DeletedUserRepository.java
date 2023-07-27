package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.sberlab.models.entities.DeletedUser;

public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {
}
