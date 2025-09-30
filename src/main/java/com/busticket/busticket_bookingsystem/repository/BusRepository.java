package com.busticket.busticket_bookingsystem.repository;

import com.busticket.busticket_bookingsystem.entity.Bus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends MongoRepository<Bus, String> {

}