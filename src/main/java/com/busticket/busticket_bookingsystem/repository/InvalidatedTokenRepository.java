package com.busticket.busticket_bookingsystem.repository;

import com.busticket.busticket_bookingsystem.entity.identity.InvalidatedToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvalidatedTokenRepository extends MongoRepository<InvalidatedToken, String> { }

