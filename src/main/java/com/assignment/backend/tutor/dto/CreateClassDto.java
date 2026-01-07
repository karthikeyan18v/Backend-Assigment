package com.assignment.backend.tutor.dto;

import java.time.Instant;

/**
 * Request payload for creating a class session.
 */
public class CreateClassDto {
    public String teacherId;
    public String title;
    public Instant startTime;
    public int durationMinutes;
    public int capacity;
}
