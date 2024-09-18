package com.example.BackEndSem4.models;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;


    @Column(name = "amount")
    private Float amount;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "phone", length = 10, nullable = false)
    private String phone;

    @Column
    private String status;
}
