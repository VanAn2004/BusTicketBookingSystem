package com.busticket.busticket_bookingsystem.repository;

import com.busticket.busticket_bookingsystem.entity.identity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
}
