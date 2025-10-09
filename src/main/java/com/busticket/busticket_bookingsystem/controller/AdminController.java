package com.busticket.busticket_bookingsystem.controller;

import com.busticket.busticket_bookingsystem.dto.response.ApiResponse;
import com.busticket.busticket_bookingsystem.dto.response.UserResponse;
import com.busticket.busticket_bookingsystem.service.AdminService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.simp.SimpMessagingTemplate; // ✅ Import Socket
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('ADMIN')") // ✅ Bảo vệ toàn bộ controller, chỉ ADMIN mới truy cập được
public class AdminController {

    AdminService adminService;
    SimpMessagingTemplate messagingTemplate; // ✅ Thêm messagingTemplate để gửi real-time

    /**
     * Lấy danh sách tất cả người dùng
     * GET -> /admin/users
     */
    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(adminService.getAllUsers())
                .build();
    }

    /**
     * Lấy thông tin một người dùng theo ID
     * GET -> /admin/users/{id}
     */
    @GetMapping("/users/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable String id) {
        return ApiResponse.<UserResponse>builder()
                .result(adminService.getUserById(id))
                .build();
    }

    /**
     * Khóa tài khoản người dùng và gửi cập nhật real-time
     * POST -> /admin/users/{id}/ban
     */
    @PostMapping("/users/{id}/ban")
    public ApiResponse<String> banUser(@PathVariable String id) {
        String result = adminService.banUserById(id);

        // ✅ Gửi danh sách người dùng đã cập nhật qua WebSocket
        broadcastUserListUpdate();

        return ApiResponse.<String>builder()
                .result(result)
                .build();
    }

    /**
     * Mở khóa tài khoản người dùng và gửi cập nhật real-time
     * POST -> /admin/users/{id}/unban
     */
    @PostMapping("/users/{id}/unban")
    public ApiResponse<String> unbanUser(@PathVariable String id) {
        String result = adminService.unbanUserById(id);

        // ✅ Gửi danh sách người dùng đã cập nhật qua WebSocket
        broadcastUserListUpdate();

        return ApiResponse.<String>builder()
                .result(result)
                .build();
    }

    /**
     * Xóa người dùng và gửi cập nhật real-time
     * DELETE -> /admin/users/{id}
     */
    @DeleteMapping("/users/{id}")
    public ApiResponse<String> deleteUser(@PathVariable String id) {
        String result = adminService.deleteUserById(id);

        // ✅ Gửi danh sách người dùng đã cập nhật qua WebSocket
        broadcastUserListUpdate();

        return ApiResponse.<String>builder()
                .result(result)
                .build();
    }

    /**
     * Hàm private để lấy và gửi danh sách người dùng mới nhất qua socket
     * Giúp tránh lặp code và đảm bảo frontend luôn đồng bộ
     */
    private void broadcastUserListUpdate() {
        List<UserResponse> updatedUsers = adminService.getAllUsers();
        messagingTemplate.convertAndSend("/topic/admin/users", updatedUsers);
    }
}