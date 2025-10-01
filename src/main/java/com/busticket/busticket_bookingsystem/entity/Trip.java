package com.busticket.busticket_bookingsystem.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "trips")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Trip {
    @Id
    String id;

    String departure;            // Điểm đi
    String destination;          // Điểm đến
    LocalDateTime departureTime; // Thời gian khởi hành
    double price;                // Giá vé
    int totalSeats;              // Tổng số ghế
    int availableSeats;          // Số ghế trống

    @DBRef
    Bus bus;                     // Xe chạy chuyến này

    @DBRef
    List<Seat> seats;            // Danh sách ghế (tùy chọn)
}


