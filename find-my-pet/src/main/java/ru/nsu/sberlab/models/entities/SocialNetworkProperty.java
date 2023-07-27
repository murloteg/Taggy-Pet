package ru.nsu.sberlab.models.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "social_networks_properties")
@Data
@NoArgsConstructor
public class SocialNetworkProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Long propertyId;

    @Column(name = "property_name")
    private String propertyValue;
}
