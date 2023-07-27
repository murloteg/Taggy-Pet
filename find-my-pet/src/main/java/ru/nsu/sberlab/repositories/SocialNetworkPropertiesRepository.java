package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.sberlab.models.entities.SocialNetworkProperty;

public interface SocialNetworkPropertiesRepository extends JpaRepository<SocialNetworkProperty, Long> {
}
