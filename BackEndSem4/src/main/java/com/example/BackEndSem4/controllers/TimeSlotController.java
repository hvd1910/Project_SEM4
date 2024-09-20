package com.example.BackEndSem4.controllers;


import com.example.BackEndSem4.dtos.TimeSlotDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.TimeSlot;
import com.example.BackEndSem4.response.Response;
import com.example.BackEndSem4.services.timeslot.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/time-slots")
@RequiredArgsConstructor
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllTimeSlots() {
        List<TimeSlot> timeSlots = timeSlotService.getAllTimeSlots();
        return ResponseEntity.ok(new Response("success", "Get time slot all  successfully.", timeSlots));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTimeSlotById(@PathVariable Long id) throws DataNotFoundException {
        TimeSlot timeSlot = timeSlotService.getTimeSlotById(id);
        return ResponseEntity.ok(new Response("success", "Update time slot successfully.", timeSlot));

    }

    @GetMapping("/specialty/{specialtyId}")
    public ResponseEntity<?> getTimeSlotsBySpecialty(@PathVariable Long specialtyId) {
        List<TimeSlot> timeSlots = timeSlotService.getTimeSlotsBySpecialty(specialtyId);
        return ResponseEntity.ok(new Response("success", "Get time slot by specialtyId successfully.", timeSlots));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createTimeSlot(@RequestBody TimeSlotDTO timeSlotDTO) throws DataNotFoundException {
        TimeSlot createdTimeSlot = timeSlotService.createTimeSlot(timeSlotDTO);
        return ResponseEntity.ok(new Response("success", "Create time slot successfully.", createdTimeSlot));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTimeSlot(@PathVariable Long id, @RequestBody TimeSlotDTO timeSlotDTO) throws DataNotFoundException {
            TimeSlot updatedTimeSlot = timeSlotService.updateTimeSlot(id, timeSlotDTO);
        return ResponseEntity.ok(new Response("success", "Update time slot successfully.", null));

    }
}
