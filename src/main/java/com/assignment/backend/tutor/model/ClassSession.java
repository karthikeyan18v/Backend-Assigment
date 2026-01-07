package com.assignment.backend.tutor.model;

import com.assignment.backend.common.exception.ApiException;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a live class session.
 */
public class ClassSession {

    private String id;
    private Instant startTime;
    private int capacity;
    private final Set<String> students = new HashSet<>();

    public synchronized void book(String studentId) {
        if (students.contains(studentId)) {
            throw new ApiException("Student has already booked this class");
        }
        if (students.size() >= capacity) {
            throw new ApiException("Class is full. No seats available");
        }
        students.add(studentId);
    }

    public void start() {
        if (Instant.now().isBefore(startTime)) {
            throw new ApiException("Class cannot start before scheduled time");
        }
    }

    public Set<String> getStudents() {
        return students;
    }

    public void setId(String id) { this.id = id; }
    public void setStartTime(Instant startTime) { this.startTime = startTime; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getId() { return id; }
}
