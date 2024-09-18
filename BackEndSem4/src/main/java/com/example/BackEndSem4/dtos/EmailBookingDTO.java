package com.example.BackEndSem4.dtos;

import com.example.BackEndSem4.models.Booking;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailBookingDTO {

    @JsonProperty("doctor_name")
    private String doctorName;

    @JsonProperty("specialty_name")
    private String specialtyName;


    @JsonProperty("clinic_name")
    private String clinicName;

    @JsonProperty("clinic_address")
    private String clinicAddress;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;

    @JsonProperty("date_schedule")
    private LocalDate dateSchedule;

    // info user

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;




    public static EmailBookingDTO fromBookingData(Booking booking) {
        booking.getUser().setRole(null);
        return EmailBookingDTO.builder()
                .doctorName(booking.getSchedule().getDoctor().getUser().getFullName())
                .specialtyName(booking.getSchedule().getDoctor().getSpecialty().getSpecialtyName())
                .clinicName(booking.getSchedule().getClinic().getClinicName())
                .clinicAddress(booking.getSchedule().getClinic().getAddress())
                .startTime(booking.getSchedule().getStartTime())
                .endTime(booking.getSchedule().getEndTime())
                .dateSchedule(booking.getSchedule().getDateSchedule())
                .fullName(booking.getUser().getFullName())
                .email(booking.getUser().getEmail())
                .phoneNumber(booking.getUser().getPhoneNumber())
                .build();
    }

}
