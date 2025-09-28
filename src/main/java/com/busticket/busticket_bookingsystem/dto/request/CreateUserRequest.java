package com.busticket.busticket_bookingsystem.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String roleName;  // ADMIN / CUSTOMER / OPERATOR
}
