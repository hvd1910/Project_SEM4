package com.example.BackEndSem4.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClinicDTO {

    @JsonProperty("clinic_name")
    private String clinicName;

    @JsonProperty("clinic_image")
    private String clinicImage;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private String address;

    private boolean active;

}
