package com.busticket.busticket_bookingsystem.controller;

import com.busticket.busticket_bookingsystem.dto.request.CreateTripRequest;
import com.busticket.busticket_bookingsystem.dto.request.FilterTripRequest;
import com.busticket.busticket_bookingsystem.dto.response.ApiResponse;
import com.busticket.busticket_bookingsystem.dto.response.TripResponse;
import com.busticket.busticket_bookingsystem.service.TripService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TripController {

    TripService tripService;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
    public ApiResponse<TripResponse> createTrip(@RequestBody CreateTripRequest request) {
        var result = tripService.createTrip(request);
        return ApiResponse.<TripResponse>builder().result(result).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
    public ApiResponse<TripResponse> updateTrip(@PathVariable String id, @RequestBody CreateTripRequest request) {
        var result = tripService.updateTrip(id, request);
        return ApiResponse.<TripResponse>builder().result(result).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
    public ApiResponse<Void> deleteTrip(@PathVariable String id) {
        tripService.deleteTrip(id);
        return ApiResponse.<Void>builder().message("Trip deleted successfully").build();
    }

    @GetMapping("/filter")
    public ApiResponse<List<TripResponse>> filterTrips(FilterTripRequest request) {
        var result = tripService.filterTrips(request);
        return ApiResponse.<List<TripResponse>>builder().result(result).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TripResponse> getTripById(@PathVariable String id) {
        var result = tripService.getTripById(id);
        return ApiResponse.<TripResponse>builder().result(result).build();
    }

    @GetMapping
    public ApiResponse<List<TripResponse>> getAllTrips() {
        var result = tripService.getAllTrips();
        return ApiResponse.<List<TripResponse>>builder()
                .result(result)
                .build();
    }

}
