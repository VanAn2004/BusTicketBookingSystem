package com.busticket.busticket_bookingsystem.service;

import com.busticket.busticket_bookingsystem.dto.request.CreateUserRequest;
import com.busticket.busticket_bookingsystem.dto.request.UpdateUserRequest;
import com.busticket.busticket_bookingsystem.dto.response.UserResponse;
import com.busticket.busticket_bookingsystem.entity.identity.Role;
import com.busticket.busticket_bookingsystem.exception.AppException;
import com.busticket.busticket_bookingsystem.exception.ErrorCode;
import com.busticket.busticket_bookingsystem.mapper.UserMapper;
import com.busticket.busticket_bookingsystem.repository.RoleRepository;
import com.busticket.busticket_bookingsystem.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    RoleRepository roleRepository;

    /**
     * Tạo user mới
     */
    /**
     * Tạo user mới với các ràng buộc
     */
    public UserResponse createUser(CreateUserRequest request) {
        // Validate username
        if (request.getUserName() == null || !request.getUserName().matches("^[a-zA-Z0-9_]{4,20}$")) {
            throw new AppException(ErrorCode.USERNAME_INVALID);
        }
        userRepository.findByUserName(request.getUserName())
                .ifPresent(existing -> {
                    throw new AppException(ErrorCode.USERNAME_EXISTED);
                });

        // Validate email
        if (request.getEmail() == null || !request.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new AppException(ErrorCode.INVALID_EMAIL);
        }
        userRepository.findByEmail(request.getEmail())
                .ifPresent(existing -> {
                    throw new AppException(ErrorCode.EMAIL_EXISTED);
                });

        // Validate phone (nếu có)
        if (request.getPhone() != null) {
            if (!request.getPhone().matches("^(0\\d{9}|\\+84\\d{9})$")) {
                throw new AppException(ErrorCode.INVALID_PHONE);
            }
            userRepository.findByPhone(request.getPhone())
                    .ifPresent(existing -> {
                        throw new AppException(ErrorCode.PHONE_EXISTED);
                    });
        }

        // Validate password
        if (request.getPassword() == null ||
                !request.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        // Tạo entity từ request
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Gán role (nếu không có thì mặc định USER)
        String roleName = request.getRoleName() != null ? request.getRoleName() : "USER";
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        user.setRole(role);

        user.setCreateAt(LocalDateTime.now());
        user.setIsActive(true);

        try {
            userRepository.save(user);
        } catch (DuplicateKeyException ex) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }




    /**
     * Lấy thông tin user hiện tại từ SecurityContext
     */
    public UserResponse getCurrentUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    /**
     * Lấy user theo id
     */
    public UserResponse getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    /**
     * Cập nhật user hiện tại
     */
    public UserResponse updateUser(UpdateUserRequest request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // check email
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            userRepository.findByEmail(request.getEmail())
                    .filter(existing -> !existing.getId().equals(userId))
                    .ifPresent(existing -> {
                        throw new AppException(ErrorCode.EMAIL_EXISTED);
                    });
            user.setEmail(request.getEmail());
        }

        // check phone
        if (request.getPhone() != null && !request.getPhone().equals(user.getPhone())) {
            userRepository.findByPhone(request.getPhone())
                    .filter(existing -> !existing.getId().equals(userId))
                    .ifPresent(existing -> {
                        throw new AppException(ErrorCode.PHONE_EXISTED);
                    });
            user.setPhone(request.getPhone());
        }

        // update các field khác
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getUserName() != null && !request.getUserName().equals(user.getUserName())) {
            userRepository.findByUserName(request.getUserName())
                    .filter(existing -> !existing.getId().equals(userId))
                    .ifPresent(existing -> {
                        throw new AppException(ErrorCode.USERNAME_EXISTED);
                    });
            user.setUserName(request.getUserName());
        }

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
