package com.example.BackEndSem4.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDTO {

    private Long id;

    @JsonProperty("doctor_id")
    @NotBlank(message = "Doctor Id cannot be blank")
    private Long doctorId;


    @JsonProperty("clinic_id")
    @NotBlank(message = "Clinic Id cannot be blank")
    private Long clinicId;

    @JsonProperty("start_time")
    @NotBlank(message = "Start Time Id cannot be blank")
    private LocalTime startTime;


    @JsonProperty("end_time")
    @NotBlank(message = "End Time Id cannot be blank")
    private LocalTime endTime;

    @JsonProperty("date_schedule")
    @NotBlank(message = "Date Schedule cannot be blank")
    private LocalDate dateSchedule;

    @JsonProperty("price")
    @NotBlank(message = "Price cannot be blank")
    private float price;

    @JsonProperty("booking_limit")
    @NotBlank(message = "Booking Litmit cannot be blank")
    private int bookingLimit;

    @JsonProperty
    private boolean active;
}
