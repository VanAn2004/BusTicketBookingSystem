package com.busticket.busticket_bookingsystem.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BusResponse {
    private String id;
    private String licensePlate;
    private String type;
    private int seatCount;
}
