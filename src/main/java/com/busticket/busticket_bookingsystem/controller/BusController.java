package com.busticket.busticket_bookingsystem.controller;

import com.busticket.busticket_bookingsystem.dto.request.CreateBusRequest;
import com.busticket.busticket_bookingsystem.dto.request.UpdateBusRequest;
import com.busticket.busticket_bookingsystem.dto.response.ApiResponse;
import com.busticket.busticket_bookingsystem.dto.response.BusResponse;
import com.busticket.busticket_bookingsystem.entity.operate.Coach;
import com.busticket.busticket_bookingsystem.service.BusService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buses")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BusController {

    BusService busService;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
    public ApiResponse<BusResponse> createBus(@Valid @RequestBody CreateBusRequest request) {
        Coach bus = Coach.builder()
                .licensePlate(request.getLicensePlate())
                .type(request.getType())
                .seatCount(request.getSeatCount())
                .build();

        Coach saved = busService.createBus(bus);
        return ApiResponse.<BusResponse>builder()
                .result(toResponse(saved))
                .build();
    }

    @GetMapping
    public ApiResponse<List<BusResponse>> getAllBuses() {
        var buses = busService.getAllBuses().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ApiResponse.<List<BusResponse>>builder()
                .result(buses)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BusResponse> getBusById(@PathVariable String id) {
        var bus = busService.getBusById(id);
        return ApiResponse.<BusResponse>builder()
                .result(toResponse(bus))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
    public ApiResponse<BusResponse> updateBus(
            @PathVariable String id,
            @Valid @RequestBody UpdateBusRequest request
    ) {
        Coach updateRequest = Coach.builder()
                .licensePlate(request.getLicensePlate())
                .type(request.getType())
                .seatCount(request.getSeatCount())
                .build();

        Coach updated = busService.updateBus(id, updateRequest);
        return ApiResponse.<BusResponse>builder()
                .result(toResponse(updated))
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
    public ApiResponse<Void> deleteBus(@PathVariable String id) {
        busService.deleteBus(id);
        return ApiResponse.<Void>builder()
                .build();
    }

    private BusResponse toResponse(Coach bus) {
        return BusResponse.builder()
                .id(bus.getId())
                .licensePlate(bus.getLicensePlate())
                .type(bus.getType())
                .seatCount(bus.getSeatCount())
                .build();
    }
}
