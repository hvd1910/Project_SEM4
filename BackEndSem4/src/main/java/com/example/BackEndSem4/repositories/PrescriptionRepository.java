package com.example.BackEndSem4.repositories;

import com.example.BackEndSem4.models.History;
import com.example.BackEndSem4.models.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findAllByHistory(History history);
}
