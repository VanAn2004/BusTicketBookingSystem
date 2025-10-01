package com.busticket.busticket_bookingsystem.entity.booking;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
@Document(collection = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    private String id;

    private String userId;
    private String tripId;
    private int seatCount;
    private String status;    // RESERVED, CANCELED
    private Instant createdAt;
}
