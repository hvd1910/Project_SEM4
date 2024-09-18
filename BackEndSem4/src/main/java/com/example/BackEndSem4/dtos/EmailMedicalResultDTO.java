package com.example.BackEndSem4.dtos;

import com.example.BackEndSem4.models.Prescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailMedicalResultDTO {
    @JsonProperty("doctor_name")
    private String doctorName;

    @JsonProperty("specialty_name")
    private String specialtyName;

    @JsonProperty("clinic_name")
    private String clinicName;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("date_schedule")
    private LocalDate dateSchedule;

    @JsonProperty("diagnosis")
    private String diagnosis;

    @JsonProperty("prescriptions")
    private Set<Prescription> prescriptions;
}
