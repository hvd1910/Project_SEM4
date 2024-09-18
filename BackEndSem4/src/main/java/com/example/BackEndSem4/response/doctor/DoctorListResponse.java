package com.example.BackEndSem4.response.doctor;


import lombok.*;

import java.util.List;
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class DoctorListResponse {
    private List<DoctorResponse> doctors;
    private int totalPages;
}
