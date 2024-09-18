package com.example.BackEndSem4.models;

import com.example.BackEndSem4.dtos.EmailBookingDTO;
import com.example.BackEndSem4.dtos.EmailMedicalResultDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@Getter
@Setter
public class Email {
    @NotBlank(message = "Email cannot be blank.")
    private String toEmail;

    @NotBlank(message = "Subject cannot be blank.")
    private String subject;

    @JsonProperty("booking_data")
    private EmailBookingDTO emailBookingDTO;

    @JsonProperty("medical_results")
    private EmailMedicalResultDTO emailMedicalResultDTO ;



}
