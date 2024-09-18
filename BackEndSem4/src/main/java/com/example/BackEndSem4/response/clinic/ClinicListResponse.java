package com.example.BackEndSem4.response.clinic;

import com.example.BackEndSem4.models.Clinic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ClinicListResponse {
    private List<Clinic> clinicList;
    private int totalPages;
}

