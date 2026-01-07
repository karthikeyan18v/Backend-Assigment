package com.assignment.backend.ride.model;

import java.time.Instant;

/**
 * Represents a ride request and its lifecycle.
 */
public class Ride {

    private String id;
    private String userId;
    private String driverId;
    private RideStatus status;
    private Instant createdAt;
    private Instant acceptedAt;
    private Instant completedAt;

    public Ride(String id, String userId) {
        this.id = id;
        this.userId = userId;
        this.status = RideStatus.REQUESTED;
        this.createdAt = Instant.now();
    }

    public boolean canTransitionTo(RideStatus target) {
        return switch (status) {
            case REQUESTED -> target == RideStatus.ACCEPTED || target == RideStatus.CANCELLED;
            case ACCEPTED -> target == RideStatus.STARTED || target == RideStatus.CANCELLED;
            case STARTED -> target == RideStatus.COMPLETED;
            default -> false;
        };
    }

    public void updateStatus(RideStatus target) {
        this.status = target;
        if (target == RideStatus.ACCEPTED) acceptedAt = Instant.now();
        if (target == RideStatus.COMPLETED) completedAt = Instant.now();
    }

    public void assignDriver(String driverId) {
        this.driverId = driverId;
    }

    public String getId() { return id; }
    public String getDriverId() { return driverId; }
    public RideStatus getStatus() { return status; }
}
