package com.busticket.busticket_bookingsystem.service;

import com.busticket.busticket_bookingsystem.entity.operate.Trip;
import com.busticket.busticket_bookingsystem.entity.booking.Booking;
import com.busticket.busticket_bookingsystem.repository.BookingRepository;
import com.busticket.busticket_bookingsystem.repository.TripRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepo;
    private final TripRepository tripRepo;
    private final SimpMessagingTemplate messagingTemplate; // thêm dòng này

    public Booking create(String userId, String tripId, int seatCount) {
        if (seatCount <= 0) {
            throw new IllegalArgumentException("seatCount must be > 0");
        }

        // Lấy trip ra để check ghế
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (trip.getAvailableSeats() < seatCount) {
            throw new RuntimeException("Not enough seats available");
        }

        // Trừ ghế
        trip.setAvailableSeats(trip.getAvailableSeats() - seatCount);
        tripRepo.save(trip);

        // Gửi cập nhật real-time cho client khác
        messagingTemplate.convertAndSend("/topic/trips/" + tripId, trip);

        // Tạo booking
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

        if (!bk.getUserId().equals(userId)) {
            throw new RuntimeException("Not your booking");
        }

        if (!"CANCELED".equals(bk.getStatus())) {
            bk.setStatus("CANCELED");
            bookingRepo.save(bk);

            // Hoàn lại ghế cho trip
            Trip trip = tripRepo.findById(bk.getTripId())
                    .orElseThrow(() -> new RuntimeException("Trip not found"));
            trip.setAvailableSeats(trip.getAvailableSeats() + bk.getSeatCount());
            tripRepo.save(trip);

            // Gửi lại cập nhật
            messagingTemplate.convertAndSend("/topic/trips/" + bk.getTripId(), trip);
        }
    }
}
