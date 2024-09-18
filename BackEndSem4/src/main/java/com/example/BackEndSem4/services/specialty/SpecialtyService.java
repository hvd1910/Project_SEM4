package com.example.BackEndSem4.services.specialty;

import com.example.BackEndSem4.dtos.SpecialtyDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Specialty;
import com.example.BackEndSem4.repositories.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SpecialtyService implements ISpecialtyService{
    private final SpecialtyRepository specialtyRepository;

    @Override
    public Page<Specialty> getSpecialtyAll(String keyword, Pageable pageable) {
        return specialtyRepository.getSpecialtyAll(keyword, pageable);
    }

    @Override
    public Specialty getSpecialtyById(Long id) throws DataNotFoundException {
        return specialtyRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Specialty not found with id " + id));

    }

    @Override
    @Transactional
    public Specialty createSpecialty(SpecialtyDTO specialtyDTO) throws DataNotFoundException {
        Optional<Specialty> existingSpecialty =
                specialtyRepository.findBySpecialtyName(specialtyDTO.getSpecialtyName());
        if (existingSpecialty.isPresent()) {
            throw new DataNotFoundException("Specialty already exists with name " + specialtyDTO.getSpecialtyName());
        }
        Specialty specialtynew = Specialty.builder()
                .specialtyName(specialtyDTO.getSpecialtyName())
                .specialtyImage(specialtyDTO.getSpecialtyImage())
                .description(specialtyDTO.getDescription())
                .build();
        specialtynew.setCreatedAt(LocalDateTime.now());
        specialtynew.setUpdatedAt(LocalDateTime.now());
        return specialtyRepository.save(specialtynew);
    }

    @Override
    public Specialty updateSpecialty(Long id, SpecialtyDTO specialtyDTO) throws DataNotFoundException {
        Specialty existingSpecialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Specialty not found with id " + id));

        Optional<Specialty> specialtyWithSameName = specialtyRepository.findBySpecialtyName(specialtyDTO.getSpecialtyName());

        if (specialtyWithSameName.isPresent() && !specialtyWithSameName.get().getId().equals(id)) {
            throw new DataNotFoundException("Specialty already exists with name " + specialtyDTO.getSpecialtyName());
        }

        existingSpecialty.setSpecialtyName(specialtyDTO.getSpecialtyName());
        existingSpecialty.setSpecialtyImage(specialtyDTO.getSpecialtyImage());
        existingSpecialty.setDescription(specialtyDTO.getDescription());
        existingSpecialty.setUpdatedAt(LocalDateTime.now());

        return specialtyRepository.save(existingSpecialty);
    }

    @Override
    public void deleteSpecialtyById(Long id) throws Exception {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Specialty not found with id " + id));
        specialtyRepository.delete(specialty);
    }
}
