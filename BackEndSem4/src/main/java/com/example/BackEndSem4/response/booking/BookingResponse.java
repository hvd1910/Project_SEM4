package com.example.BackEndSem4.response.booking;

import com.example.BackEndSem4.dtos.EmailBookingDTO;
import com.example.BackEndSem4.models.Booking;
import com.example.BackEndSem4.models.Schedule;
import com.example.BackEndSem4.response.BaseResponse;
import com.example.BackEndSem4.response.schedule.ScheduleResponse;
import com.example.BackEndSem4.response.user.UserResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse extends BaseResponse {
    private Long id;

    @JsonProperty("schedule")
    private ScheduleResponse scheduleResponse;

    @JsonProperty("user")
    private UserResponse userResponse;


    @JsonProperty
    private float amount;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_code")
    private String paymentCode;
    @JsonProperty
    private String status;



    public static BookingResponse fromBookingResponse(Booking booking) {
        booking.getUser().setRole(null);
        BookingResponse bookingResponse = BookingResponse.builder()
                .id(booking.getId())
                .userResponse(UserResponse.fromUser(booking.getUser()))
                .scheduleResponse(ScheduleResponse.fromSchedule(booking.getSchedule()))
                .amount(booking.getAmount())
                .paymentMethod(booking.getPaymentMethod())
                .paymentCode(booking.getPaymentCode())
                .status(booking.getStatus())
                .build();
        bookingResponse.setCreatedAt(booking.getCreatedAt());
        bookingResponse.setUpdatedAt(booking.getUpdatedAt());

        return bookingResponse;
    }


}
