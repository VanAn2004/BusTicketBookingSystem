package com.busticket.busticket_bookingsystem.service;

import com.busticket.busticket_bookingsystem.entity.booking.Booking;
import com.busticket.busticket_bookingsystem.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepo;

    public Booking create(String userId, String tripId, int seatCount) {
        if (seatCount <= 0) throw new IllegalArgumentException("seatCount must be > 0");

        Booking bk = Booking.builder()
                .userId(userId)
                .tripId(tripId)
                .seatCount(seatCount)
                .status("RESERVED")
                .createdAt(Instant.now())
                .build();
        return bookingRepo.save(bk);
    }

    public List<Booking> myBookings(String userId) {
        return bookingRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public void cancel(String bookingId, String userId) {
        Booking bk = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if (!bk.getUserId().equals(userId))
            throw new RuntimeException("Not your booking");
        if (!"CANCELED".equals(bk.getStatus())) {
            bk.setStatus("CANCELED");
            bookingRepo.save(bk);
        }
    }
}