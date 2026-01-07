package com.assignment.backend.tutor.controller;

import com.assignment.backend.tutor.dto.*;
import com.assignment.backend.tutor.model.ClassSession;
import com.assignment.backend.tutor.service.ClassService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * REST APIs for tutor class management.
 */
@RestController
@RequestMapping("/classes")
public class ClassController {

    private final ClassService service;

    public ClassController(ClassService service) {
        this.service = service;
    }

    @PostMapping
    public ClassCreatedResponseDto create(@RequestBody CreateClassDto dto) {
        return service.create(dto);
    }

    @PostMapping("/{id}/book")
    public BookingResponseDto book(@PathVariable String id,
                     @RequestBody BookSeatDto dto) {
        return service.book(id, dto.studentId);
    }

    @GetMapping("/{id}/attendees")
    public AttendeesResponseDto attendees(@PathVariable String id) {
        return service.attendees(id);
    }

    @PostMapping("/{id}/start")
    public ClassStartResponseDto start(@PathVariable String id) {
        return service.start(id);
    }
}

