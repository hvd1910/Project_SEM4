package com.example.BackEndSem4.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlotDTO {

    @JsonProperty("duration_minutes")
    @NotBlank(message = "Duration Minutes is required")
    private int durationMinutes;


    @JsonProperty("specialty_id")
    @NotBlank(message = "Specialty Id is required")
    private Long specialtyId;
}
