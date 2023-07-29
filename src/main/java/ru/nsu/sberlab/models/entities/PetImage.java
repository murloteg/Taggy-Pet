package ru.nsu.sberlab.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pet_images")
@Data
@NoArgsConstructor
public class PetImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "image_uuid_name")
    private String imageUUIDName;
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "petImage")
    private Pet pet;

    public PetImage(String imagePath, String imageUUIDName) {
        this.imagePath = imagePath;
        this.imageUUIDName = imageUUIDName;
    }
}
