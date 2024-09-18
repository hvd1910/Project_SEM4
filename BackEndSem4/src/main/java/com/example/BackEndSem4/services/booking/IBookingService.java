package com.example.BackEndSem4.services.booking;

import com.example.BackEndSem4.dtos.BookingDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Booking;

import java.util.List;

public interface IBookingService {
    Booking createBooking(BookingDTO bookingDTO) throws DataNotFoundException;
    Booking updateBooking(Long id, BookingDTO bookingDTO) throws DataNotFoundException;
    Booking updateBookingStatus(Long id, String status) throws DataNotFoundException;
    Booking updateBookingStatusUser(Long id, String status) throws DataNotFoundException;

    void deleteBooking(Long id) throws DataNotFoundException;
    Booking getBookingById(Long id);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByUserId(Long userId);

}
