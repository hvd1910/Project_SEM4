package com.example.BackEndSem4.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "time_slots")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;


    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;

    @Column(name = "active", nullable = false)
    private boolean active;


}

