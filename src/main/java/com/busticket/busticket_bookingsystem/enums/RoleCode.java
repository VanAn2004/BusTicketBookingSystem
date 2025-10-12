package com.busticket.busticket_bookingsystem.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RoleCode {

    CUSTOMER("CUSTOMER"),
    OPERATOR("OPERATOR"),
    ADMIN("ADMIN");
    String roleName;

}
