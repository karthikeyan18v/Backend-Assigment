package com.assignment.backend.ride.controller;


import com.assignment.backend.ride.dto.*;
import com.assignment.backend.ride.service.RideService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * REST APIs for ride operations.
 */
@RestController
@RequestMapping("/rides")
public class RideController {

    private final RideService service;

    public RideController(RideService service) {
        this.service = service;
    }

    @PostMapping("/request")
    public RideResponseDto request(@Valid @RequestBody RideRequestDto dto) {
        return service.requestRide(dto);
    }

    @PostMapping("/{id}/status")
    public StatusUpdateResponseDto update(@PathVariable String id,
                       @RequestBody RideStatusUpdateDto dto) {
        return service.updateStatus(id, dto.status);
    }
}

