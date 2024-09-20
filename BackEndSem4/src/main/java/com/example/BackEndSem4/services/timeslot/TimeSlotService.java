package com.example.BackEndSem4.services.timeslot;

import com.example.BackEndSem4.dtos.TimeSlotDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Specialty;
import com.example.BackEndSem4.models.TimeSlot;
import com.example.BackEndSem4.repositories.SpecialtyRepository;
import com.example.BackEndSem4.repositories.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final SpecialtyRepository specialtyRepository;

    public List<TimeSlot> getTimeSlotsBySpecialty(Long specialtyId) {
        return timeSlotRepository.findBySpecialtyId(specialtyId);
    }

    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    public TimeSlot getTimeSlotById(Long id) throws DataNotFoundException {
        return timeSlotRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Time Slot not found with id " + id));
    }

    @Transactional
    public TimeSlot createTimeSlot(TimeSlotDTO timeSlotDTO) throws DataNotFoundException {
        List<TimeSlot> timeSlots = timeSlotRepository.findBySpecialtyId(timeSlotDTO.getSpecialtyId());
        if(!timeSlots.isEmpty()) {
            throw new DataNotFoundException("Already exists at a Time Slot with Specialty.Please try again");
        }
        Specialty existingSpecialty = specialtyRepository.findById(timeSlotDTO.getSpecialtyId())
                .orElseThrow(() -> new DataNotFoundException("Specialty not found with id " + timeSlotDTO.getSpecialtyId()));


        TimeSlot timeSlot = TimeSlot.builder()
                .durationMinutes(timeSlotDTO.getDurationMinutes())
                .specialty(existingSpecialty)
                .active(true)
                .build();

        return timeSlotRepository.save(timeSlot);
    }

    @Transactional
    public TimeSlot updateTimeSlot(Long id, TimeSlotDTO updatedTimeSlot) throws DataNotFoundException {
        Optional<TimeSlot> existingTimeSlot = timeSlotRepository.findById(id);

        if (existingTimeSlot.isPresent()) {
            TimeSlot timeSlot = existingTimeSlot.get();
            timeSlot.setDurationMinutes(updatedTimeSlot.getDurationMinutes());
            return timeSlotRepository.save(timeSlot);
        } else {
            throw new DataNotFoundException("TimeSlot not found with id: " + id);
        }
    }

}