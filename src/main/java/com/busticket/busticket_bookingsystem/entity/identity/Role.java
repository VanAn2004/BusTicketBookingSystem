package com.busticket.busticket_bookingsystem.entity.identity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    private String id;

    @Indexed(unique = true)   // đảm bảo không trùng name
    private String name;

    private String description;
}
