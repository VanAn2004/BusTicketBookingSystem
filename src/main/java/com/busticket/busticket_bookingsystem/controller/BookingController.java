package com.busticket.busticket_bookingsystem.controller;

import com.busticket.busticket_bookingsystem.entity.booking.Booking;
import com.busticket.busticket_bookingsystem.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    // Lấy userId từ token (nếu team đã set username = userId thì dùng getName();
    // nếu khác, tạm thời mock để test nhanh)
    private String currentUserId(Authentication auth) {
        return (auth != null) ? auth.getName() : "customer1"; // fallback để demo
    }

    @PostMapping
    public Booking create(@RequestParam String tripId,
                          @RequestParam int seatCount,
                          Authentication auth) {
        return bookingService.create(currentUserId(auth), tripId, seatCount);
    }

    @GetMapping("/me")
    public List<Booking> myBookings(Authentication auth) {
        return bookingService.myBookings(currentUserId(auth));
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable String id, Authentication auth) {
        bookingService.cancel(id, currentUserId(auth));
    }
}
