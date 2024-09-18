package com.example.BackEndSem4.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "histories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class History extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "diagnosis")
    private String diagnosis;

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
    private Set<Prescription> prescriptions;

    @Column(name = "file_prescription")
    private String filePrescription;



}
