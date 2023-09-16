package ru.nsu.sberlab.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.sberlab.models.enums.Sex;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "chip_id", unique = true) // TODO: add also stamp_id column and pet_club_id column
    private String chipId;

    @Column(name = "type")
    private String type;

    @Column(name = "breed")
    private String breed;

    @Column(name = "sex")
    private Sex sex;

    @Column(name = "pet_name")
    private String name;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "pets_features",
            joinColumns = {@JoinColumn(name = "pet_id")},
            inverseJoinColumns = {@JoinColumn(name = "feature_id")}
    )
    private List<Feature> features = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "pets")
    private List<User> users = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private PetImage petImage;

    public Pet(String chipId, String type, String breed, Sex sex, String name, List<Feature> features, PetImage petImage) {
        this.chipId = chipId;
        this.type = type;
        this.breed = breed;
        this.sex = sex;
        this.name = name;
        this.features = features;
        this.petImage = petImage;
    }
}
