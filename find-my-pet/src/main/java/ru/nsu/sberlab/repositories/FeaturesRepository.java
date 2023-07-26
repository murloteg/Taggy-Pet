package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.sberlab.models.entities.Feature;

public interface FeaturesRepository extends JpaRepository<Feature, Long> {
    void deleteFeatureByFeatureId(Long featureId);
}
