package com.busticket.busticket_bookingsystem.entity.identity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    String id;

    String userName;
    String password;
    String email;
    String firstName;
    String lastName;
    String phone;
    LocalDateTime createAt;
    Boolean isActive;


    Role role;   // tham chiếu tới collection Role
}
