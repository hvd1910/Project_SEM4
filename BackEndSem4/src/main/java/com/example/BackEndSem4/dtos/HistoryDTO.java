package com.example.BackEndSem4.dtos;

import com.example.BackEndSem4.models.Prescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDTO {

    @JsonProperty("booking_id")
    private Long bookingId;

    @JsonProperty("diagnosis")
    private String diagnosis;

    @JsonProperty("prescriptions")
    private Set<Prescription> prescriptions;

    @JsonProperty("file_prescription")
    private String filePrescription;

}
