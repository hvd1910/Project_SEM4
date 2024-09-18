package com.example.BackEndSem4.services.schedule;

import com.example.BackEndSem4.dtos.ScheduleDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Booking;
import com.example.BackEndSem4.models.Clinic;
import com.example.BackEndSem4.models.Doctor;
import com.example.BackEndSem4.models.Schedule;
import com.example.BackEndSem4.repositories.BookingRepository;
import com.example.BackEndSem4.repositories.ClinicRepository;
import com.example.BackEndSem4.repositories.DoctorRepository;
import com.example.BackEndSem4.repositories.ScheduleReponsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService{

    private final ScheduleReponsitory scheduleRepository;

    private final BookingRepository bookingRepository;

    private final DoctorRepository doctorRepository;

    private final ClinicRepository clinicRepository;


    @Override
    public Page<Schedule> getAllSchedules(Long clinicId, Long doctorId, LocalDate dateSchedule,
                                          String keyword, Pageable pageable) {


        return scheduleRepository.getAllSchedules(clinicId, doctorId, dateSchedule, keyword, pageable);
    }

    @Override
    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }


    @Override
    public Schedule createSchedule(ScheduleDTO scheduleDTO) throws DataNotFoundException {

        Doctor doctor = doctorRepository.findById(scheduleDTO.getDoctorId())
                .orElseThrow(() -> new DataNotFoundException("Doctor not found"));
        Clinic clinic = clinicRepository.findById(scheduleDTO.getClinicId())
                .orElseThrow(() -> new DataNotFoundException("Clinic not found"));

        // Kiểm tra xem bác sĩ đó có lịch vào khung giờ đó chưa
        Schedule scheduleExisting = scheduleRepository.findScheduleCheck(
                scheduleDTO.getDoctorId(),scheduleDTO.getStartTime(), scheduleDTO.getDateSchedule());

        if(scheduleExisting != null) {
            throw new DataNotFoundException("The schedule already exists, please check again.");
        }

        Schedule schedule = Schedule.builder()
                .doctor(doctor)
                .clinic(clinic)
                .startTime(scheduleDTO.getStartTime())
                .endTime(scheduleDTO.getEndTime())
                .dateSchedule(scheduleDTO.getDateSchedule())
                .price(scheduleDTO.getPrice())
                .bookingLimit(scheduleDTO.getBookingLimit())
                .active(true)
                .build();

        return scheduleRepository.save(schedule);
    }



    @Override
    public Schedule updateSchedule(Long id, ScheduleDTO scheduleDTO) throws DataNotFoundException {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        Doctor doctor = doctorRepository.findById(scheduleDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Clinic clinic = clinicRepository.findById(scheduleDTO.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        // Kiểm tra xem bác sĩ đó có lịch vào khung giờ đó chưa
        Schedule scheduleExisting = scheduleRepository.findScheduleCheck(
                scheduleDTO.getDoctorId(),scheduleDTO.getStartTime(), scheduleDTO.getDateSchedule());

        if(scheduleExisting != null && !scheduleExisting.getId().equals(id)) {
            throw new DataNotFoundException("Repeat data schedule with other schedules, please check again.");
        }

        schedule.setDoctor(doctor);
        schedule.setClinic(clinic);
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setEndTime(scheduleDTO.getEndTime());
        schedule.setDateSchedule(scheduleDTO.getDateSchedule());
        schedule.setPrice(scheduleDTO.getPrice());
        schedule.setBookingLimit(scheduleDTO.getBookingLimit());
        schedule.setActive(scheduleDTO.isActive());

        schedule.setUpdatedAt(LocalDateTime.now());
        return scheduleRepository.save(schedule);
    }



    @Override
    public void deleteSchedule(Long id) throws DataNotFoundException {
        // Check kiểm tra để xóa mềm hay xóa cứng
        List<Booking> bookingList = bookingRepository.findAllByScheduleId(id);
        if(!bookingList.isEmpty()) {
            throw new DataNotFoundException("A booking has been made and cannot be deleted.");
        }

        scheduleRepository.deleteById(id);
    }



    @Override
    public List<Schedule> getSchedulesByDoctorId(Long doctorId, LocalDate dateSchedule) {
        return scheduleRepository.findByDoctorId(doctorId, dateSchedule);
    }

}
