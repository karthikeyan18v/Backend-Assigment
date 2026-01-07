package com.assignment.backend.config;


import com.assignment.backend.ride.model.Driver;
import com.assignment.backend.ride.repository.DriverRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Seeds initial driver data at application startup.
 */
@Component
public class DriverSeeder {

    private final DriverRepository repository;

    public DriverSeeder(DriverRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void seedDrivers() {
        repository.save(new Driver("d1", 12.95, 77.59));
        repository.save(new Driver("d2", 12.96, 77.60));
        repository.save(new Driver("d3", 12.97, 77.61));
        repository.save(new Driver("d4", 13.45, 73.69));
    }
}
