package com.example.BackEndSem4.services.clinic;

import com.example.BackEndSem4.dtos.ClinicDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Clinic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IClinicService {
    Page<Clinic> getClinicAll(String keyword, Pageable pageable);
    Clinic getClinicById(Long id) throws DataNotFoundException;
    Clinic createClinic(ClinicDTO clinicDTO) throws DataNotFoundException;

    Clinic updateClinic(Long id, ClinicDTO clinicDTO) throws DataNotFoundException;

    void deleteClinicById(Long id) throws Exception;
}
