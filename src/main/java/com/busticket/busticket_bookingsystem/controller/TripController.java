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

    /**
     * Tạo chuyến xe mới
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
    public ApiResponse<TripResponse> createTrip(@RequestBody CreateTripRequest request) {
        var result = tripService.createTrip(request);
        return ApiResponse.<TripResponse>builder()
                .result(result)
                .build();
    }

    /**
     * Lọc chuyến theo điều kiện (tuyến, giá, số ghế trống)
     */
    @GetMapping("/filter")
    public ApiResponse<List<TripResponse>> filterTrips(FilterTripRequest request) {
        var result = tripService.filterTrips(request);
        return ApiResponse.<List<TripResponse>>builder()
                .result(result)
                .build();
    }

    /**
     * Xem chi tiết chuyến
     */
    @GetMapping("/{id}")
    public ApiResponse<TripResponse> getTripById(@PathVariable String id) {
        var result = tripService.getTripById(id);
        return ApiResponse.<TripResponse>builder()
                .result(result)
                .build();
    }
}
