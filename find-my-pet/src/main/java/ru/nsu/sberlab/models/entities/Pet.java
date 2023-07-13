package ru.nsu.sberlab.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.sberlab.models.enums.Sex;

import java.util.List;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pet_id")
    private Integer id;

    @Column(name = "chip_id")
    private String chipId;

    @Column(name = "type")
    private String type;

    @Column(name = "breed")
    private String breed;

    @Column(name = "sex")
    private Sex sex;

    @Column(name = "pet_name")
    private String name;

    @Column(name = "has_features")
    private boolean hasFeatures;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pets")
    private List<Feature> features;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Pet(String chipId, String type, String breed, Sex sex, String name) {
        this.chipId = chipId;
        this.type = type;
        this.breed = breed;
        this.sex = sex;
        this.name = name;
    }
}