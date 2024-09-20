package com.example.BackEndSem4.repositories;

import com.example.BackEndSem4.models.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUserId(Long userId);

    List<Booking> findAllByScheduleId(Long sheduleId);

    @Query("SELECT b FROM Booking b " +
            "JOIN b.user u " +
            "JOIN b.schedule s " +
            "WHERE (:dateBooking IS NULL OR FUNCTION('DATE', b.createdAt) = :dateBooking) " + // Lọc theo ngày
            "AND (:keyword IS NULL OR " +
            "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " + // Tìm theo fullName của user
            "LOWER(b.paymentCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " + // Tìm theo paymentCode
            "LOWER(CAST(s.id AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(CAST(b.id AS string)) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:status IS NULL OR :status = '' OR LOWER(b.status) = LOWER(:status))") // Tìm theo schedule_id
    Page<Booking> findAllBookings(@Param("dateBooking") LocalDate dateBooking,
                                  @Param("keyword") String keyword,
                                  @Param("status") String status,
                                  Pageable pageable);

}
