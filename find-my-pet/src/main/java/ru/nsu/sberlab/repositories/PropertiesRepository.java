package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.sberlab.models.entities.PropertyType;

public interface PropertiesRepository extends JpaRepository<PropertyType, Long> {

}
