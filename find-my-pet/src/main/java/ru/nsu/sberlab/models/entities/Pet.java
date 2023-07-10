package ru.nsu.sberlab.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.sberlab.models.enums.Sex;

import javax.persistence.*;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "chip")
    private String chipId;

    @Column(name = "type")
    private String type;

    @Column(name = "breed")
    private String breed;

    @Column(name = "sex")
    private Sex sex;

    @Column(name = "pet_name")
    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Pet(Long id, String chipId, String type, String breed, Sex sex, String name) {
        this.id = id;
        this.chipId = chipId;
        this.type = type;
        this.breed = breed;
        this.sex = sex;
        this.name = name;
    }
}