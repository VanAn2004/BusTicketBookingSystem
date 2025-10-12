package com.busticket.busticket_bookingsystem.service;

import com.busticket.busticket_bookingsystem.dto.request.CreateTripRequest;
import com.busticket.busticket_bookingsystem.dto.response.BusResponse;
import com.busticket.busticket_bookingsystem.dto.response.TripResponse;
import com.busticket.busticket_bookingsystem.entity.operate.Coach;
import com.busticket.busticket_bookingsystem.entity.operate.Trip;
import com.busticket.busticket_bookingsystem.repository.BusRepository;
import com.busticket.busticket_bookingsystem.repository.SeatRepository;
import com.busticket.busticket_bookingsystem.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.busticket.busticket_bookingsystem.dto.request.FilterTripRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final BusRepository busRepository;
    private final SeatRepository seatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // 🔹 Tạo trip mới
    public TripResponse createTrip(CreateTripRequest request) {
        Coach bus = busRepository.findById(request.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        // Tạo danh sách ghế mặc định
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= bus.getSeatCount(); i++) {
            Seat seat = Seat.builder()
                    .seatNumber("A" + i)
                    .booked(false)
                    .build();
            seatRepository.save(seat);
            seats.add(seat);
        }

        Trip trip = Trip.builder()
                .departure(request.getDeparture())
                .destination(request.getDestination())
                .departureTime(request.getDepartureTime())
                .price(request.getPrice())
                .totalSeats(bus.getSeatCount())
                .availableSeats(bus.getSeatCount())
                .bus(bus)
                .seats(seats)
                .build();

        Trip saved = tripRepository.save(trip);
        messagingTemplate.convertAndSend("/topic/trips", toResponse(saved));

        return toResponse(saved);
    }

    // 🔹 Cập nhật thông tin chuyến
    public TripResponse updateTrip(String id, CreateTripRequest request) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        Coach bus = busRepository.findById(request.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        trip.setDeparture(request.getDeparture());
        trip.setDestination(request.getDestination());
        trip.setDepartureTime(request.getDepartureTime());
        trip.setPrice(request.getPrice());
        trip.setBus(bus);
        trip.setTotalSeats(bus.getSeatCount());

        // Tính lại số ghế trống
        int available = (int) trip.getSeats().stream().filter(s -> !s.isBooked()).count();
        trip.setAvailableSeats(available);

        Trip updated = tripRepository.save(trip);
        messagingTemplate.convertAndSend("/topic/trips", toResponse(updated));

        return toResponse(updated);
    }

    // 🔹 Lấy danh sách tất cả chuyến
    public List<TripResponse> getAllTrips() {
        return tripRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // 🔹 Lấy chi tiết chuyến
    public TripResponse getTripById(String id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        return toResponse(trip);
    }

    // 🔹 Xoá chuyến
    public void deleteTrip(String id) {
        if (!tripRepository.existsById(id)) {
            throw new RuntimeException("Trip not found");
        }
        tripRepository.deleteById(id);
        messagingTemplate.convertAndSend("/topic/trips/deleted", id);
    }

    // 🔹 Map entity → DTO
    private TripResponse toResponse(Trip trip) {
        return TripResponse.builder()
                .id(trip.getId())
                .departure(trip.getDeparture())
                .destination(trip.getDestination())
                .departureTime(trip.getDepartureTime())
                .price(trip.getPrice())
                .availableSeats(trip.getAvailableSeats())
                .bus(BusResponse.builder()
                        .id(trip.getBus().getId())
                        .licensePlate(trip.getBus().getLicensePlate())
                        .type(trip.getBus().getType())
                        .seatCount(trip.getBus().getSeatCount()) // ✅ tổng ghế ở đây
                        .build())
                .build();
    }

    public List<TripResponse> filterTrips(FilterTripRequest request) {
        if (request == null) {
            return tripRepository.findAll()
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        }

        return tripRepository.findAll().stream()
                .filter(t -> {
                    String departure = request.getDeparture();
                    return departure == null || departure.isBlank() ||
                            t.getDeparture().equalsIgnoreCase(departure);
                })
                .filter(t -> {
                    String destination = request.getDestination();
                    return destination == null || destination.isBlank() ||
                            t.getDestination().equalsIgnoreCase(destination);
                })
                .filter(t -> request.getMinPrice() == null || t.getPrice() >= request.getMinPrice())
                .filter(t -> request.getMaxPrice() == null || t.getPrice() <= request.getMaxPrice())
                .filter(t -> request.getMinAvailableSeats() == null || t.getAvailableSeats() >= request.getMinAvailableSeats())
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


}
