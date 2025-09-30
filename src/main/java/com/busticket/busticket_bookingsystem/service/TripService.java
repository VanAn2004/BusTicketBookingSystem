package com.busticket.busticket_bookingsystem.service;

import com.busticket.busticket_bookingsystem.dto.request.CreateTripRequest;
import com.busticket.busticket_bookingsystem.dto.request.FilterTripRequest;
import com.busticket.busticket_bookingsystem.dto.response.TripResponse;
import com.busticket.busticket_bookingsystem.entity.Bus;
import com.busticket.busticket_bookingsystem.entity.Trip;
import com.busticket.busticket_bookingsystem.repository.BusRepository;
import com.busticket.busticket_bookingsystem.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final BusRepository busRepository;

    public TripResponse createTrip(CreateTripRequest request) {
        Bus bus = busRepository.findById(request.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        Trip trip = Trip.builder()
                .departure(request.getDeparture())
                .destination(request.getDestination())
                .departureTime(request.getDepartureTime())
                .price(request.getPrice())
                .totalSeats(request.getTotalSeats())
                .availableSeats(request.getTotalSeats())
                .bus(bus)
                .build();

        Trip saved = tripRepository.save(trip);

        return toResponse(saved);
    }

    public List<TripResponse> filterTrips(FilterTripRequest request) {
        List<Trip> trips = tripRepository.findAll();

        return trips.stream()
                .filter(t -> request.getDeparture() == null || t.getDeparture().equalsIgnoreCase(request.getDeparture()))
                .filter(t -> request.getDestination() == null || t.getDestination().equalsIgnoreCase(request.getDestination()))
                .filter(t -> request.getMinPrice() == null || t.getPrice() >= request.getMinPrice())
                .filter(t -> request.getMaxPrice() == null || t.getPrice() <= request.getMaxPrice())
                .filter(t -> request.getMinAvailableSeats() == null || t.getAvailableSeats() >= request.getMinAvailableSeats())
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TripResponse getTripById(String id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        return toResponse(trip);
    }

    private TripResponse toResponse(Trip trip) {
        return TripResponse.builder()
                .id(trip.getId())
                .departure(trip.getDeparture())
                .destination(trip.getDestination())
                .departureTime(trip.getDepartureTime())
                .price(trip.getPrice())
                .availableSeats(trip.getAvailableSeats())
                .busLicensePlate(trip.getBus().getLicensePlate())
                .build();
    }
}
