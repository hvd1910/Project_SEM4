package com.example.BackEndSem4.services.booking;

import com.example.BackEndSem4.dtos.BookingDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Booking;
import com.example.BackEndSem4.models.History;
import com.example.BackEndSem4.models.Schedule;
import com.example.BackEndSem4.models.User;
import com.example.BackEndSem4.repositories.BookingRepository;
import com.example.BackEndSem4.repositories.HistoryRepository;
import com.example.BackEndSem4.repositories.ScheduleReponsitory;
import com.example.BackEndSem4.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService{

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final ScheduleReponsitory scheduleRepository;

    private final HistoryRepository historyRepository;

    @Override
    public Booking createBooking(BookingDTO bookingDTO) throws DataNotFoundException {
        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        Schedule schedule = scheduleRepository.findById(bookingDTO.getScheduleId())
                .orElseThrow(() -> new DataNotFoundException("Schedule not found"));

        List<Booking> bookingList = bookingRepository.findAllByScheduleId(bookingDTO.getScheduleId());
        if (bookingList.size() >= schedule.getBookingLimit()) {
            throw new DataNotFoundException("This schedule has reached its set limit.Please try again.");
        }
        // Kết hợp tất cả các chuỗi lại
        Long idNumber = generateUniqueScheduleId(schedule, bookingDTO);


        Booking booking = Booking.builder()
                .id(idNumber)
                .user(user)
                .schedule(schedule)
                .amount(bookingDTO.getAmount())
                .paymentMethod(bookingDTO.getPaymentMethod())
                .status(Booking.PENDING)
                .build();

        Booking booking1 = bookingRepository.save(booking);

        History history = History.builder()
                .booking(booking1)
                .build();

        History history1 = historyRepository.save(history);
        return booking1;
    }


    // GenerateId
    public Long generateUniqueScheduleId(Schedule schedule, BookingDTO bookingDTO) {
        String doctorId = schedule.getDoctor().getId().toString();
        String userId = bookingDTO.getUserId().toString();
        String startTime = schedule.getStartTime().format(DateTimeFormatter.ofPattern("HHmm"));
        String dateSchedule = schedule.getDateSchedule().format(DateTimeFormatter.ofPattern("ddMMyy"));

        Random random = new Random();
        Long idNumber;
        boolean idExists;

        // Vòng lặp để tìm một idNumber không trùng với bất kỳ schedule nào
        do {
            // Tạo số ngẫu nhiên 2 chữ số
            int randomTwoDigits = 10 + random.nextInt(90);

            // Kết hợp tất cả các chuỗi lại
            idNumber = Long.valueOf(doctorId + startTime + dateSchedule + userId + randomTwoDigits);

            // Kiểm tra xem idNumber có tồn tại trong cơ sở dữ liệu hay không
            Optional<Schedule> existingSchedule = scheduleRepository.findById(idNumber);
            idExists = existingSchedule.isPresent(); // true nếu id đã tồn tại

        } while (idExists); // Lặp lại nếu id đã tồn tại

        return idNumber;
    }

    @Override
    public Booking updateBooking(Long id, BookingDTO bookingDTO) throws DataNotFoundException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Booking not found"));

        booking.setPaymentMethod(bookingDTO.getPaymentMethod());
        booking.setPaymentCode(bookingDTO.getPaymentCode());
        booking.setStatus(bookingDTO.getStatus());
        booking.setUpdatedAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBookingStatus(Long id, String status) throws DataNotFoundException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Booking not found"));

        booking.setStatus(status);
        booking.setUpdatedAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBookingStatusUser(Long id, String status) throws DataNotFoundException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Booking not found"));

        if (!canRejectedBooking(booking)) {
            throw new DataNotFoundException("Booking cannot be deleted within 24 hours of the scheduled time.");
        }

        booking.setStatus(status);
        booking.setUpdatedAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }


    public boolean canRejectedBooking(Booking booking) {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();
        // Kết hợp dateSchedule và startTime để có thời gian lịch khám đầy đủ
        LocalDateTime scheduleDateTime = LocalDateTime.of(booking.getSchedule().getDateSchedule(), booking.getSchedule().getStartTime());
        // Kiểm tra nếu thời gian hiện tại nằm trong khoảng 1 ngày trước khi lịch khám bắt đầu
        LocalDateTime oneDayBeforeSchedule = scheduleDateTime.minusDays(1);

        return now.isBefore(oneDayBeforeSchedule);
    }

    @Override
    public void deleteBooking(Long id) throws DataNotFoundException {
        if (!bookingRepository.existsById(id)) {
            throw new DataNotFoundException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findAllByUserId(userId);
    }

}
