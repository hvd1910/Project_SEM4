package com.example.BackEndSem4.services.doctor;

import com.example.BackEndSem4.dtos.DoctorDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IDoctorService {
    Page<Doctor> getDoctorsBySpecialtyIdAndName(Long specialtyId, String name, Pageable pageable);
    Doctor getDoctorById(Long id) throws DataNotFoundException;
    Doctor createDoctor(DoctorDTO doctorDTO) throws DataNotFoundException;

    Doctor updateDoctor(Long id, DoctorDTO doctorDTO) throws DataNotFoundException;

    void deleteDoctorById(Long id) throws Exception;
}
