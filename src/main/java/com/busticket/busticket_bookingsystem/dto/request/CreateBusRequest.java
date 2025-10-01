package com.busticket.busticket_bookingsystem.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateBusRequest {
    @NotBlank
    private String licensePlate; // biển số

    @NotBlank
    private String type; // loại xe (ghế ngồi/giường nằm)

    @Min(10)
    private int seatCount; // số ghế
}
