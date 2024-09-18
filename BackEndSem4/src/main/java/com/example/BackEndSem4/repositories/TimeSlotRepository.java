package com.example.BackEndSem4.repositories;

import com.example.BackEndSem4.models.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findBySpecialtyId(Long specialtyId);
}
