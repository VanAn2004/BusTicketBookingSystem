package com.busticket.busticket_bookingsystem.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    private String userName;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
}

