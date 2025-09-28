package com.busticket.busticket_bookingsystem.repository;

import com.busticket.busticket_bookingsystem.entity.identity.ResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResetTokenRepository extends MongoRepository<ResetToken, String> {
}
