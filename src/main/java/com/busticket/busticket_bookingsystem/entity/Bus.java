package com.busticket.busticket_bookingsystem.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "buses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bus {
    @Id
    String id;

    String licensePlate; // Biển số
    String type;         // Loại xe (ghế ngồi/giường nằm)
    int seatCount;       // Tổng số ghế
}


