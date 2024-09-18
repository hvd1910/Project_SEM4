package com.example.BackEndSem4.services.timeslot;

import com.example.BackEndSem4.dtos.TimeSlotDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Specialty;
import com.example.BackEndSem4.models.TimeSlot;
import com.example.BackEndSem4.repositories.SpecialtyRepository;
import com.example.BackEndSem4.repositories.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final SpecialtyRepository specialtyRepository;

    public List<TimeSlot> getTimeSlotsBySpecialty(Long specialtyId) {
        return timeSlotRepository.findBySpecialtyId(specialtyId);
    }

    public TimeSlot createTimeSlot(TimeSlotDTO timeSlotDTO) throws DataNotFoundException {
        Specialty existingSpecialty = specialtyRepository.findById(timeSlotDTO.getSpecialtyId())
                .orElseThrow(() -> new DataNotFoundException("Specialty not found with id " + timeSlotDTO.getSpecialtyId()));

        TimeSlot timeSlot = TimeSlot.builder()
                .durationMinutes(timeSlotDTO.getDurationMinutes())
                .specialty(existingSpecialty)
                .active(true)
                .build();

        return timeSlotRepository.save(timeSlot);
    }


}