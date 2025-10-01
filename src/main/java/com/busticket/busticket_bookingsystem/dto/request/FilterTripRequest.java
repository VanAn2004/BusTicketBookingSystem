package com.busticket.busticket_bookingsystem.dto.request;

import lombok.Data;

@Data
public class FilterTripRequest {
    private String departure;
    private String destination;
    private Double minPrice;
    private Double maxPrice;
    private Integer minAvailableSeats;
}


