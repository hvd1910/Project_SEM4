package com.example.BackEndSem4.controllers;


import com.example.BackEndSem4.dtos.ScheduleDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Schedule;
import com.example.BackEndSem4.response.Response;
import com.example.BackEndSem4.response.schedule.ScheduleListReponse;
import com.example.BackEndSem4.response.schedule.ScheduleResponse;
import com.example.BackEndSem4.services.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/schedules")
@RequiredArgsConstructor
public class SchedulController {

    private final ScheduleService scheduleService;

    // Lấy tất cả lịch khám
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> getAllSchedules(@RequestParam(defaultValue = "") Long specialtyId,
                                             @RequestParam(defaultValue = "") Long doctorId,
                                             @RequestParam(defaultValue = "") LocalDate dateSchedule,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "999999999") int limit,
                                             @RequestParam(defaultValue = "") String sort,
                                             @RequestParam(defaultValue = "") String keyword) {

        Sort.Direction sortDirection = "asc".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by(sortDirection,"id")
        );



        Page<ScheduleResponse> schedules = scheduleService.getAllSchedules(specialtyId, doctorId, dateSchedule, keyword, pageRequest)
                .map(ScheduleResponse::fromSchedule);

        int totalPages = schedules.getTotalPages();
        List<ScheduleResponse> doctorResponseList = schedules.getContent();
        return ResponseEntity.ok(new Response("success", "Get schedules all successfully.",
                ScheduleListReponse.builder()
                        .scheduleResponseList(doctorResponseList)
                        .totalPages(totalPages)
                        .build()));

    }

    // Lấy lịch khám theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getScheduleById(@PathVariable Long id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(new Response("success", "Get schedule successfully.", ScheduleResponse.fromSchedule(schedule)));

    }

    // Tạo mới lịch khám
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleDTO scheduleDTO) throws DataNotFoundException {
            Schedule newSchedule = scheduleService.createSchedule(scheduleDTO);
        return ResponseEntity.ok(new Response("success", "Create schedule successfully.", ScheduleResponse.fromSchedule(newSchedule)));

    }

    // Cập nhật lịch khám
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id, @RequestBody ScheduleDTO scheduleDTO) throws DataNotFoundException {

            Schedule updatedSchedule = scheduleService.updateSchedule(id, scheduleDTO);
        return ResponseEntity.ok(new Response("success", "Update schedule successfully.", null));

    }

    // Xóa lịch khám
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) throws DataNotFoundException {
            scheduleService.deleteSchedule(id);
        return ResponseEntity.ok(new Response("success", "Delete schedule successfully.", null));

    }

    // Lấy lịch khám theo Doctor ID
    @GetMapping("/doctor")
    public ResponseEntity<?> getSchedulesByDoctorId(@RequestParam(defaultValue = "") Long doctorId,
                                                    @RequestParam(defaultValue = "") LocalDate dateSchedule) {
        List<ScheduleResponse> schedules = scheduleService.getSchedulesByDoctorId(doctorId, dateSchedule)
                .stream().map(ScheduleResponse::fromSchedule).toList();
        return ResponseEntity.ok(new Response("success", "Get schedules by doctor successfully.", schedules));
    }


}
