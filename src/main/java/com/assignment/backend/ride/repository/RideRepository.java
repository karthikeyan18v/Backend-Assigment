package com.assignment.backend.ride.repository;

import com.assignment.backend.ride.model.Ride;
import org.springframework.stereotype.Repository;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory repository for rides.
 */
@Repository
public class RideRepository {

    private final Map<String, Ride> store = new ConcurrentHashMap<>();

    public void save(Ride ride) {
        store.put(ride.getId(), ride);
    }

    public Ride findById(String id) {
        return store.get(id);
    }
}
