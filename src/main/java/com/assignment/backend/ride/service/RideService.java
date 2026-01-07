package com.assignment.backend.ride.service;


import com.assignment.backend.common.exception.ApiException;
import com.assignment.backend.ride.dto.*;
import com.assignment.backend.ride.model.*;
import com.assignment.backend.ride.repository.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Business logic for ride operations.
 */
@Service
public class RideService {

    private final RideRepository rideRepo;
    private final DriverRepository driverRepo;

    public RideService(RideRepository rideRepo, DriverRepository driverRepo) {
        this.rideRepo = rideRepo;
        this.driverRepo = driverRepo;
    }

    public RideResponseDto requestRide(RideRequestDto request) {
        Ride ride = new Ride(UUID.randomUUID().toString(), request.userId);

        Driver driver = driverRepo.findNearestAvailable(
                request.pickup.lat, request.pickup.lon);

        if (driver != null) {
            driver.setAvailable(false);
            ride.assignDriver(driver.getId());
        }

        rideRepo.save(ride);

        RideResponseDto response = new RideResponseDto();
        response.rideId = ride.getId();
        response.status = ride.getStatus().name();
        response.assignedDriverId = ride.getDriverId();
        response.etaMinutes = driver == null ? 0 : 5;

        return response;
    }

    public StatusUpdateResponseDto updateStatus(String rideId, String status) {
        Ride ride = rideRepo.findById(rideId);
        
        if (ride == null) {
            throw new ApiException("Ride not found with id: " + rideId);
        }

        RideStatus target;
        try {
            target = RideStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new ApiException("Invalid status: " + status + ". Valid statuses are: REQUESTED, ACCEPTED, STARTED, COMPLETED, CANCELLED");
        }

        if (!ride.canTransitionTo(target)) {
            throw new ApiException(getTransitionErrorMessage(ride.getStatus(), target));
        }

        ride.updateStatus(target);
        
        if (target == RideStatus.COMPLETED || target == RideStatus.CANCELLED) {
            if (ride.getDriverId() != null) {
                Driver driver = driverRepo.findById(ride.getDriverId());
                if (driver != null) {
                    driver.setAvailable(true);
                }
            }
        }
        
        StatusUpdateResponseDto response = new StatusUpdateResponseDto();
        response.rideId = rideId;
        response.status = target.name();
        response.message = getSuccessMessage(target);
        return response;
    }

    private String getTransitionErrorMessage(RideStatus current, RideStatus target) {
        if (current == RideStatus.COMPLETED) {
            return "Cannot change status of a completed ride. Please create a new ride request.";
        }
        if (current == RideStatus.CANCELLED) {
            return "Cannot change status of a cancelled ride. Please create a new ride request.";
        }
        return String.format("Invalid status transition from %s to %s. Allowed transitions: %s",
                current, target, getAllowedTransitions(current));
    }

    private String getAllowedTransitions(RideStatus current) {
        return switch (current) {
            case REQUESTED -> "ACCEPTED, CANCELLED";
            case ACCEPTED -> "STARTED, CANCELLED";
            case STARTED -> "COMPLETED";
            default -> "None";
        };
    }

    private String getSuccessMessage(RideStatus status) {
        return switch (status) {
            case ACCEPTED -> "Ride accepted successfully. Driver is on the way.";
            case STARTED -> "Ride started successfully. Enjoy your trip!";
            case COMPLETED -> "Ride completed successfully. Thank you for riding with us!";
            case CANCELLED -> "Ride cancelled successfully.";
            default -> "Ride status updated successfully.";
        };
    }
}
