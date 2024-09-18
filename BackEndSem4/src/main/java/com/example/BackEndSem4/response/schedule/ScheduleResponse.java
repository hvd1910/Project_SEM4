package com.example.BackEndSem4.response.schedule;


import com.example.BackEndSem4.models.Schedule;
import com.example.BackEndSem4.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponse extends BaseResponse {
    private Long id;

    @JsonProperty("doctor_id")
    private Long doctorId;

    @JsonProperty("doctor_name")
    private String doctorName;

    @JsonProperty("specialty_id")
    private Long specialtyId;

    @JsonProperty("specialty_name")
    private String specialtyName;

    @JsonProperty("clinic_id")
    private Long clinicId;

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

    @JsonProperty("price")
    private float price;


    @JsonProperty("booking_limit")
    private int bookingLimit;

    @JsonProperty
    private boolean active;

    public static ScheduleResponse fromSchedule(Schedule schedule) {
        ScheduleResponse scheduleResponse =   ScheduleResponse.builder()
                .id(schedule.getId())
                .doctorId(schedule.getDoctor().getId())
                .doctorName(schedule.getDoctor().getUser().getFullName())
                .specialtyId(schedule.getDoctor().getSpecialty().getId())
                .specialtyName(schedule.getDoctor().getSpecialty().getSpecialtyName())
                .clinicId(schedule.getClinic().getId())
                .clinicName(schedule.getClinic().getClinicName())
                .clinicAddress(schedule.getClinic().getAddress())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .dateSchedule(schedule.getDateSchedule())
                .price(schedule.getPrice())
                .bookingLimit(schedule.getBookingLimit())
                .active(schedule.isActive())
                .build();
        scheduleResponse.setCreatedAt(schedule.getCreatedAt());
        scheduleResponse.setUpdatedAt(schedule.getUpdatedAt());
        return scheduleResponse;
    }
}
