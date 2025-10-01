package com.busticket.busticket_bookingsystem.repository;

import com.busticket.busticket_bookingsystem.entity.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends MongoRepository<Trip, String> {
    List<Trip> findByDepartureAndDestination(String departure, String destination);
    List<Trip> findByPriceBetween(double min, double max);
    List<Trip> findByAvailableSeatsGreaterThanEqual(int seats);
}
