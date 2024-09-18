package com.example.BackEndSem4.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clinics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Clinic extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clinic_name", length = 100)
    private String clinicName;

    @Column(name = "clinic_image")
    private String clinicImage;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "phone", length = 10, nullable = false)
    private String phone;

    @Column(name = "address", length = 250)
    private String address;

    private boolean active;
}
