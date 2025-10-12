package com.busticket.busticket_bookingsystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends MongoRepository<Seat, String> {
}
