package com.assignment.backend.ride.dto;

/**
 * Response returned after requesting a ride.
 */
public class RideResponseDto {
    public String rideId;
    public String status;
    public String assignedDriverId;
    public Integer etaMinutes;
}