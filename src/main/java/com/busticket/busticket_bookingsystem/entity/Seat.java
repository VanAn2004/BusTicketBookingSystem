package com.busticket.busticket_bookingsystem.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "seats")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Seat {
    @Id
    String id;

    String seatNumber;  // Số ghế
    boolean booked;     // Trạng thái (true = đã đặt, false = trống)
}