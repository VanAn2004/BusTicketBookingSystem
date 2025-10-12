package com.busticket.busticket_bookingsystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    Optional<List<User>> findAllByFirstNameContainingIgnoreCase(String firstName);
    Optional<User> findByPhone(String phone);


}