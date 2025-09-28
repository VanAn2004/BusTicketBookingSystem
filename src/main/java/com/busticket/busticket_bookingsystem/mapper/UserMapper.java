package com.busticket.busticket_bookingsystem.mapper;

import com.busticket.busticket_bookingsystem.dto.request.CreateUserRequest;
import com.busticket.busticket_bookingsystem.dto.response.UserResponse;
import com.busticket.busticket_bookingsystem.entity.identity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "role.name", target = "roleName")
    @Mapping(source = "createAt", target = "createdAt")
    UserResponse toUserResponse(User user);
    User toUser(CreateUserRequest createUserRequest);
}
