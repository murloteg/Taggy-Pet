package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.sberlab.models.entities.FeatureProperty;

@Repository
public interface FeaturePropertiesRepository extends JpaRepository<FeatureProperty, Long> {
}
