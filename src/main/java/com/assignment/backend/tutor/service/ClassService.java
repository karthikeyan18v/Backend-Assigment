package com.assignment.backend.tutor.service;

import com.assignment.backend.common.exception.ApiException;
import com.assignment.backend.tutor.dto.*;
import com.assignment.backend.tutor.model.ClassSession;
import com.assignment.backend.tutor.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

/**
 * Business logic for class scheduling and booking.
 */
@Service
public class ClassService {

    private final ClassRepository repository = new ClassRepository();

    public ClassCreatedResponseDto create(CreateClassDto dto) {
        ClassSession session = new ClassSession();
        session.setId(UUID.randomUUID().toString());
        session.setStartTime(dto.startTime);
        session.setCapacity(dto.capacity);

        repository.save(session);
        
        ClassCreatedResponseDto response = new ClassCreatedResponseDto();
        response.message = "Class created successfully";
        response.classId = session.getId();
        response.capacity = dto.capacity;
        return response;
    }

    public BookingResponseDto book(String classId, String studentId) {
        ClassSession session = repository.findById(classId);
        if (session == null) {
            throw new ApiException("Class not found with id: " + classId);
        }
        session.book(studentId);
        
        BookingResponseDto response = new BookingResponseDto();
        response.message = "Seat booked successfully";
        response.classId = classId;
        response.studentId = studentId;
        return response;
    }

    public AttendeesResponseDto attendees(String classId) {
        ClassSession session = repository.findById(classId);
        if (session == null) {
            throw new ApiException("Class not found with id: " + classId);
        }
        
        AttendeesResponseDto response = new AttendeesResponseDto();
        response.classId = classId;
        response.students = session.getStudents();
        response.totalAttendees = session.getStudents().size();
        return response;
    }

    public ClassStartResponseDto start(String classId) {
        ClassSession session = repository.findById(classId);
        if (session == null) {
            throw new ApiException("Class not found with id: " + classId);
        }
        session.start();
        
        ClassStartResponseDto response = new ClassStartResponseDto();
        response.message = "Class started successfully";
        response.classId = classId;
        return response;
    }
}
