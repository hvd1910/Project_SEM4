package com.example.BackEndSem4.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medication_name", length = 250, nullable = false)
    private String medicationName;
}
