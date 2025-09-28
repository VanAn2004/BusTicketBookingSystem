package com.busticket.busticket_bookingsystem.controller;

import com.busticket.busticket_bookingsystem.dto.request.CreateUserRequest;
import com.busticket.busticket_bookingsystem.dto.request.UpdateUserRequest;
import com.busticket.busticket_bookingsystem.dto.response.ApiResponse;
import com.busticket.busticket_bookingsystem.dto.response.UserResponse;
import com.busticket.busticket_bookingsystem.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)

public class UserController {
    UserService userService;

    @PostMapping("/registration")
    public ApiResponse<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        var result = userService.createUser(createUserRequest);
        return ApiResponse.<UserResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getCurrentUser())
                .build();

    }

    @PutMapping("/update")
    public ApiResponse<UserResponse> updateUser(@RequestBody UpdateUserRequest request) {
        var result = userService.updateUser(request);
        return ApiResponse.<UserResponse>builder().result(result).build();
    }
    @GetMapping("/user/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }
}
