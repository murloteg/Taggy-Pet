package ru.nsu.sberlab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sberlab.models.entities.PropertyType;
import ru.nsu.sberlab.repositories.PropertiesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertiesRepository propertiesRepository;

    public List<PropertyType> properties() {
        return propertiesRepository.findAll();
    }
}
