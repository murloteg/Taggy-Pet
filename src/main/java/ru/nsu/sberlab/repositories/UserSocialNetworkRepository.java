package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.sberlab.models.entities.UserSocialNetwork;

@Repository
public interface UserSocialNetworkRepository extends JpaRepository<UserSocialNetwork, Long> {
    void deleteBySocialNetworkId(Long socialNetworkId);
}
