package com.busticket.busticket_bookingsystem.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreateTripRequest {
    private String departure;
    private String destination;
    private LocalDateTime departureTime;
    private double price;
    private int totalSeats;
    private String busId; // xe chạy
}


