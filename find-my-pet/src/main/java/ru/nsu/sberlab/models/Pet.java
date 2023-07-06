package ru.nsu.sberlab.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

@Entity
@Table(name = "pets")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "chip")
    @NotBlank // TODO: add specified message.
    private String chipId;

    @Column(name = "type")
    @NotBlank
    private String type;

    @Column(name = "breed")
    @NotBlank
    private String breed;

    @Column(name = "sex")
    @NotBlank
    private String sex;

    @Column(name = "pet_name")
    @NotBlank
    private String name;

    @Column(name = "dateOfBirth")
    @NotBlank
    private String dateOfBirth;
}
