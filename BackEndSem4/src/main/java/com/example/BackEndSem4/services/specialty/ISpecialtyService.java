package com.example.BackEndSem4.services.specialty;

import com.example.BackEndSem4.dtos.SpecialtyDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISpecialtyService {
    Page<Specialty> getSpecialtyAll(String keyword, Pageable pageable);
    Specialty getSpecialtyById(Long id) throws DataNotFoundException;
    Specialty createSpecialty(SpecialtyDTO specialtyDTO) throws DataNotFoundException;

    Specialty updateSpecialty(Long id, SpecialtyDTO specialtyDTO) throws DataNotFoundException;

    void deleteSpecialtyById(Long id) throws Exception;

}
