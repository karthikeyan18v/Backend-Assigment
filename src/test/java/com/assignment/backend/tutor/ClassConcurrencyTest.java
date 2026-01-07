package com.assignment.backend.tutor;


import com.assignment.backend.common.exception.ApiException;
import com.assignment.backend.tutor.dto.CreateClassDto;
import com.assignment.backend.tutor.service.ClassService;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Concurrency test for class booking.
 *
 * Simulates multiple students booking the same class at the same time
 * and verifies that overbooking does NOT occur.
 */
class ClassConcurrencyTest {

    @Test
    void shouldNotAllowOverbookingUnderConcurrency() throws InterruptedException {

        ClassService service = new ClassService();

        // Create class with capacity = 5
        CreateClassDto dto = new CreateClassDto();
        dto.teacherId = "t1";
        dto.title = "Math";
        dto.startTime = Instant.now().plusSeconds(3600);
        dto.durationMinutes = 60;
        dto.capacity = 5;

        var session = service.create(dto);
        String classId = session.classId;

        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        // 10 students trying to book 5 seats
        for (int i = 0; i < threads; i++) {
            final String studentId = "s" + i;
            executor.submit(() -> {
                try {
                    service.book(classId, studentId);
                } catch (ApiException ignored) {
                    // Expected for students beyond capacity
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // Assert final booking count never exceeds capacity
        assertEquals(5, service.attendees(classId).students.size());
    }
}
