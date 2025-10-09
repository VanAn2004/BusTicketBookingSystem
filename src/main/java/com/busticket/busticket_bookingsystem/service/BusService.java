package com.busticket.busticket_bookingsystem.service;

import com.busticket.busticket_bookingsystem.entity.Bus;
import com.busticket.busticket_bookingsystem.entity.Trip;
import com.busticket.busticket_bookingsystem.repository.BusRepository;
import com.busticket.busticket_bookingsystem.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map; // ✅ CẦN THÊM DÒNG IMPORT NÀY

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;
    private final TripRepository tripRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // ✅ Tạo bus mới + gửi real-time
    public Bus createBus(Bus bus) {
        Bus saved = busRepository.save(bus);
        messagingTemplate.convertAndSend("/topic/buses", saved);
        return saved;
    }

    // ✅ Lấy tất cả bus
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    // ✅ Lấy bus theo id
    public Bus getBusById(String id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found with id: " + id));
    }

    // ✅ Cập nhật bus + đồng bộ trip + gửi real-time
    public Bus updateBus(String id, Bus busRequest) {
        Bus existing = getBusById(id);
        existing.setLicensePlate(busRequest.getLicensePlate());
        existing.setType(busRequest.getType());
        existing.setSeatCount(busRequest.getSeatCount());

        Bus updated = busRepository.save(existing);

        // 🔹 Đồng bộ lại tất cả trip dùng bus này
        List<Trip> trips = tripRepository.findAll().stream()
                .filter(t -> t.getBus() != null && id.equals(t.getBus().getId()))
                .toList();

        for (Trip trip : trips) {
            trip.setBus(updated);

            // Nếu số ghế xe thay đổi, cập nhật totalSeats + availableSeats
            trip.setTotalSeats(updated.getSeatCount());
            if (trip.getAvailableSeats() > updated.getSeatCount()) {
                trip.setAvailableSeats(updated.getSeatCount());
            }

            tripRepository.save(trip);
            messagingTemplate.convertAndSend("/topic/trips/" + trip.getId(), trip);
        }

        // 🔹 Gửi thông báo bus update real-time
        messagingTemplate.convertAndSend("/topic/buses", updated);

        return updated;
    }

    // ✅ Xóa bus + xóa hoặc đánh dấu trip liên quan + real-time
    public void deleteBus(String id) {
        busRepository.deleteById(id);

        // Xử lý các trip đang dùng bus này
        List<Trip> affectedTrips = tripRepository.findAll().stream()
                .filter(t -> t.getBus() != null && id.equals(t.getBus().getId()))
                .toList();

        for (Trip trip : affectedTrips) {
            trip.setBus(null); // Đánh dấu bus đã bị xóa khỏi trip
            tripRepository.save(trip);
            messagingTemplate.convertAndSend("/topic/trips/" + trip.getId(), trip);
        }

        // 🔥 SỬA CHỮA: Gửi payload có cờ 'action: DELETE' đến topic chính (/topic/buses)
        Map<String, String> deleteMessage = Map.of(
                "id", id,
                "action", "DELETE" // Frontend sẽ nhận và dùng cờ này để xóa item khỏi state
        );
        messagingTemplate.convertAndSend("/topic/buses", deleteMessage);
    }
}