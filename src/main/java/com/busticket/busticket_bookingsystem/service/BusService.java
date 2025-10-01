package com.busticket.busticket_bookingsystem.service;

import com.busticket.busticket_bookingsystem.entity.Bus;
import com.busticket.busticket_bookingsystem.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;

    public Bus createBus(Bus bus) {
        return busRepository.save(bus);
    }

    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public Bus getBusById(String id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found with id: " + id));
    }

    public Bus updateBus(String id, Bus busRequest) {
        Bus existing = getBusById(id);
        existing.setLicensePlate(busRequest.getLicensePlate());
        existing.setType(busRequest.getType());
        existing.setSeatCount(busRequest.getSeatCount());
        return busRepository.save(existing);
    }

    public void deleteBus(String id) {
        busRepository.deleteById(id);
    }
}

