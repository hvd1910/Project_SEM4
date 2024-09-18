package com.example.BackEndSem4.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "specialties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Specialty extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specialty_name", length = 150, nullable = false)
    private String specialtyName;

    @Column(name = "specialty_image")
    private String specialtyImage;

    @Column(name = "description")
    private String description;

}
