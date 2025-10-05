package com.busticket.busticket_bookingsystem.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {
    String userName;
    String token;
    boolean authenticated;

    //google - facebook
    UserResponse user; // ✅ thêm để trả về thông tin user
}
