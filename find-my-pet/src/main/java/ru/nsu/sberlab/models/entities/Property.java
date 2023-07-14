package ru.nsu.sberlab.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Long propertyId;

    @Column(name = "property_value")
    private String propertyValue;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    private List<Feature> features = new ArrayList<>();
}
