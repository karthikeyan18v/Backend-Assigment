package com.assignment.backend.ride.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class LocationDto {

    @Min(-90) @Max(90)
    public double lat;

    @Min(-180) @Max(180)
    public double lon;
}

