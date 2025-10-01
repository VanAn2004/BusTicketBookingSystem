package com.busticket.busticket_bookingsystem.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateBusRequest {
    @NotBlank
    private String licensePlate;

    @NotBlank
    private String type;

    @Min(10)
    private int seatCount;
}
