package com.assignment.backend.ride.repository;


import com.assignment.backend.ride.model.Driver;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory repository for drivers.
 */
@Repository
public class DriverRepository {

    private final Map<String, Driver> drivers = new ConcurrentHashMap<>();

    public void save(Driver driver) {
        drivers.put(driver.getId(), driver);
    }

    public Driver findNearestAvailable(double lat, double lon) {
        return drivers.values().stream()
                .filter(Driver::isAvailable)
                .findFirst()
                .orElse(null);
    }

    public Driver findById(String id) {
        return drivers.get(id);
    }

    public List<Driver> findNearby(double lat, double lon, double radiusKm) {
        return drivers.values().stream()
                .filter(Driver::isAvailable)
                .toList();
    }
}
