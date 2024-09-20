package com.example.BackEndSem4.repositories;

import com.example.BackEndSem4.models.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleReponsitory extends JpaRepository<Schedule, Long> {



    @Query("SELECT s FROM Schedule s WHERE " +
                "(:specialtyId IS NULL OR s.doctor.specialty.id = :specialtyId) AND " +
                "(:doctorId IS NULL OR s.doctor.id = :doctorId) AND " +
                "(:dateSchedule IS NULL OR s.dateSchedule = :dateSchedule) AND " +
                "(:keyword IS NULL OR " +
                "(LOWER(s.doctor.specialty.specialtyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                "LOWER(s.doctor.user.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                "CAST(s.doctor.specialty.id AS string) LIKE CONCAT('%', :keyword, '%') OR " +
                "CAST(s.doctor.id AS string) LIKE CONCAT('%', :keyword, '%') OR " +
                "CAST(s.id AS string) LIKE CONCAT('%', :keyword, '%')))")
    Page<Schedule> getAllSchedules(@Param("specialtyId") Long specialtyId,
                                   @Param("doctorId") Long doctorId,
                                   @Param("dateSchedule") LocalDate dateSchedule,
                                   @Param("keyword") String keyword,
                                   Pageable pageable);


    @Query("SELECT s FROM Schedule s WHERE " +
            "(:doctorId IS NULL OR s.doctor.id = :doctorId) AND " +
            "(:dateSchedule IS NULL OR s.dateSchedule = :dateSchedule)")
    List<Schedule> findByDoctorId(@Param("doctorId") Long doctorId,
                                  @Param("dateSchedule") LocalDate dateSchedule);



    @Query("SELECT s FROM Schedule s WHERE " +
            "s.doctor.id = :doctorId AND " +
            "s.startTime = :startTime AND " +
            "s.dateSchedule = :dateSchedule")
    Schedule findScheduleCheck(@Param("doctorId") Long doctorId,
                               @Param("startTime") LocalTime startTime,
                               @Param("dateSchedule") LocalDate dateSchedule);



}
