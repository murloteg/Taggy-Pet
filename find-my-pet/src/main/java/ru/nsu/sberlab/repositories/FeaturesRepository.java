package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.sberlab.models.entities.Feature;

@Repository
public interface FeaturesRepository extends JpaRepository<Feature, Long> {
    void deleteFeatureByFeatureId(Long featureId);
}
