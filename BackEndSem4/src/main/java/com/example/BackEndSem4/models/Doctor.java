package com.example.BackEndSem4.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "bio")
    private String bio;

    @Column(name = "experience")
    private Float experience;

    @Column(name = "qualification")
    private String qualification;

    @Column
    private boolean active;

}
