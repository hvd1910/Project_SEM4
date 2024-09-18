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
public class SpecialtyDTO {

    @JsonProperty("specialty_name")
    @NotBlank(message = "Specialty name is required")
    private String specialtyName;

    @JsonProperty("specialty_image")
    private String specialtyImage;

    @JsonProperty("description")
    private String description;
}
