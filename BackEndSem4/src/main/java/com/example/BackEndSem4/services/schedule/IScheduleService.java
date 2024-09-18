package com.example.BackEndSem4.services.schedule;

import com.example.BackEndSem4.dtos.ScheduleDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IScheduleService {
    Schedule createSchedule(ScheduleDTO scheduleDTO) throws DataNotFoundException;

    Schedule updateSchedule(Long id, ScheduleDTO scheduleDTO) throws DataNotFoundException;

    void deleteSchedule(Long id) throws DataNotFoundException;

    Schedule getScheduleById(Long id);

    Page<Schedule> getAllSchedules(Long clinicId, Long doctorId, LocalDate dateSchedule,
                                          String keyword, Pageable pageable);

    List<Schedule> getSchedulesByDoctorId(Long doctorId, LocalDate dateSchedule);

}
