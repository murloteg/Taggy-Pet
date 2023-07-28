package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.sberlab.models.entities.SocialNetwork;

@Repository
public interface SocialNetworkPropertiesRepository extends JpaRepository<SocialNetwork, Long> {
}
