package com.example.BackEndSem4.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDTO {
    @JsonProperty("user_id")
    @Min(value = 1, message = "User's ID must be > 0")
    private Long userId;

    @JsonProperty("specialty_id")
    @Min(value = 1, message = "Specialty's ID must be > 0")
    private Long specialtyId;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("experience")
    private Float experience;

    @JsonProperty("qualification")
    private String qualification;

    @JsonProperty("active")
    private boolean active;
}
