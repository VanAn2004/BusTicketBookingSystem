package com.busticket.busticket_bookingsystem.dto;

import com.busticket.busticket_bookingsystem.enums.RoleCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenPermissionDto {

    private String username;

    private String screen;

    private List<RoleCode> roles;
}
