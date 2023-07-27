package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.sberlab.models.entities.FeatureProperty;

public interface FeaturePropertiesRepository extends JpaRepository<FeatureProperty, Long> {
}
