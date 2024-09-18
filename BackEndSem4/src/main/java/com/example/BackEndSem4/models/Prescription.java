package com.example.BackEndSem4.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "history_id")
    @JsonIgnore
    private History history;

    @Column(name = "medicine")
    private String medicine;

    @Column(name = "desciption_usage")
    private String desciptionUsage;

    @Column(name = "unit")
    private Float unit;

}
