package com.example.BackEndSem4.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {


    @JsonProperty("schedule_id")
    @NotBlank(message = "Schedule id is required")
    private Long scheduleId;

    @JsonProperty("user_id")
    @NotBlank(message = "Schedule id is required")
    private Long userId;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_code")
    private String paymentCode;

    @JsonProperty("amount")
    private float amount;

    @JsonProperty("status")
    private String status;

}
