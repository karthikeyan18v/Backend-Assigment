package com.assignment.backend.ride;


import com.assignment.backend.common.exception.ApiException;
import com.assignment.backend.ride.dto.LocationDto;
import com.assignment.backend.ride.dto.RideRequestDto;
import com.assignment.backend.ride.model.Driver;
import com.assignment.backend.ride.repository.DriverRepository;
import com.assignment.backend.ride.repository.RideRepository;
import com.assignment.backend.ride.service.RideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RideService.
 * Covers driver assignment and status transition validation.
 */
class RideServiceTest {

    private RideService rideService;
    private RideRepository rideRepository;
    private DriverRepository driverRepository;

    @BeforeEach
    void setup() {
        rideRepository = new RideRepository();
        driverRepository = new DriverRepository();

        // Seed one available driver
        driverRepository.save(new Driver("d1", 12.95, 77.59));

        rideService = new RideService(rideRepository, driverRepository);
    }

    @Test
    void shouldAssignDriverWhenAvailable() {
        RideRequestDto request = new RideRequestDto();
        request.userId = "u1";

        LocationDto pickup = new LocationDto();
        pickup.lat = 12.95;
        pickup.lon = 77.59;

        LocationDto drop = new LocationDto();
        drop.lat = 12.96;
        drop.lon = 77.60;

        request.pickup = pickup;
        request.drop = drop;

        var response = rideService.requestRide(request);

        assertNotNull(response.rideId);
        assertEquals("REQUESTED", response.status);
        assertEquals("d1", response.assignedDriverId);
    }

    @Test
    void shouldRejectInvalidStatusTransition() {
        RideRequestDto request = new RideRequestDto();
        request.userId = "u2";
        request.pickup = new LocationDto();
        request.drop = new LocationDto();

        var response = rideService.requestRide(request);

        // REQUESTED → COMPLETED is invalid
        assertThrows(ApiException.class, () ->
                rideService.updateStatus(response.rideId, "COMPLETED")
        );
    }
}
