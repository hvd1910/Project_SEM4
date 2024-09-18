package com.example.BackEndSem4.response.specialty;

import com.example.BackEndSem4.models.Specialty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class SpecialtyListResponse {
    private List<Specialty> specialtyList;
    private int totalPages;
}