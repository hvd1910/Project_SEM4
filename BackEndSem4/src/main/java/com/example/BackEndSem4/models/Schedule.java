package com.example.BackEndSem4.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;


    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "date_schedule")
    private LocalDate dateSchedule;

    @Column(name = "price")
    private float price;

    @Column(name = "booking_limit")
    private int bookingLimit;

    @Column
    private boolean active;


}
