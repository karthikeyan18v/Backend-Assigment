package com.assignment.backend.ride.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a ride.
 */
public class RideRequestDto {

    @NotBlank
    public String userId;

    @NotNull
    public LocationDto pickup;

    @NotNull
    public LocationDto drop;
}

