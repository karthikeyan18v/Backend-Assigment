package com.assignment.backend.ride.controller;

import com.assignment.backend.ride.model.Driver;
import com.assignment.backend.ride.repository.DriverRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs for driver discovery.
 */
@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverRepository repository;

    public DriverController(DriverRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/nearby")
    public List<Driver> nearby(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam double radiusKm) {
        return repository.findNearby(lat, lon, radiusKm);
    }
}
