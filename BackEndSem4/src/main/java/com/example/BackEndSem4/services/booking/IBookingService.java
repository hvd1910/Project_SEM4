package com.example.BackEndSem4.services.booking;

import com.example.BackEndSem4.dtos.BookingDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Booking;
import com.example.BackEndSem4.response.booking.BookingListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IBookingService {
    Booking createBooking(BookingDTO bookingDTO) throws DataNotFoundException;
    Booking updateBooking(Long id, BookingDTO bookingDTO) throws DataNotFoundException;
    Booking updateBookingStatus(Long id, String status) throws DataNotFoundException;
    Booking updateBookingStatusUser(Long id, String status) throws DataNotFoundException;

    void deleteBooking(Long id) throws DataNotFoundException;
    Booking getBookingById(Long id);
    BookingListResponse getAllBookings(LocalDate dateBooking, String keyword,String status, Pageable pageable);
    List<Booking> getBookingsByUserId(Long userId);

}
