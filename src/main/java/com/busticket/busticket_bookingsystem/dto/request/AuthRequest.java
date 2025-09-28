package com.busticket.busticket_bookingsystem.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    private String userName;
    private String password;
}
