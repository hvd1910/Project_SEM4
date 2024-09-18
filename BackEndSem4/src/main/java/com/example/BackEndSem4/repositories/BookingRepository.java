package com.example.BackEndSem4.repositories;

import com.example.BackEndSem4.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUserId(Long userId);

    List<Booking> findAllByScheduleId(Long sheduleId);
}
