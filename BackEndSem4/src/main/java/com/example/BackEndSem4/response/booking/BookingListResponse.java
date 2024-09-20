package com.example.BackEndSem4.response.booking;

import com.example.BackEndSem4.models.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class BookingListResponse {
    private List<BookingResponse> bookingList;
    private int totalPages;
}
