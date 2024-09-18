package com.example.BackEndSem4.response.history;

import com.example.BackEndSem4.models.History;
import com.example.BackEndSem4.models.Prescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryResponse {
    private  Long id;

    @JsonProperty("booking_id")
    private Long bookingId;

    @JsonProperty("diagnosis")
    private String diagnosis;

    @JsonProperty("prescriptions")
    private Set<Prescription> prescriptions;

    @JsonProperty("file_prescription")
    private String filePrescription;


    public static HistoryResponse fromHistoryResponse(History history) {

        HistoryResponse historyResponse = HistoryResponse.builder()
                .id(history.getId())
                .bookingId(history.getBooking().getId())
                .diagnosis(history.getDiagnosis())
                .prescriptions(history.getPrescriptions())
                .filePrescription(history.getFilePrescription())
                .build();
        history.setCreatedAt(history.getCreatedAt());
        history.setUpdatedAt(history.getUpdatedAt());

        return historyResponse;
    }
}
