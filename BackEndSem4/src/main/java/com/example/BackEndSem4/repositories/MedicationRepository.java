package com.example.BackEndSem4.repositories;

import com.example.BackEndSem4.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    Optional<Medication> findByMedicationName(String medicationName);
}