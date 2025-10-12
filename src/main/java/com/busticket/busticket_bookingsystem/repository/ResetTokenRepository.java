package com.busticket.busticket_bookingsystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResetTokenRepository extends MongoRepository<ResetToken, String> {
}
