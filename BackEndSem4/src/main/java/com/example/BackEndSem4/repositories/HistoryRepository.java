package com.example.BackEndSem4.repositories;

import com.example.BackEndSem4.models.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
    History findHistoryByBooking_Id(Long bookingId);
}
