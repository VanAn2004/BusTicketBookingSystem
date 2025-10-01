package com.busticket.busticket_bookingsystem.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TripResponse {
    private String id;
    private String departure;
    private String destination;
    private LocalDateTime departureTime;
    private double price;
    private int availableSeats;
    private String busLicensePlate;
}

