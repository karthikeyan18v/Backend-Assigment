package com.assignment.backend.tutor.repository;

import com.assignment.backend.tutor.model.ClassSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory repository for class sessions.
 */
public class ClassRepository {

    private final Map<String, ClassSession> store = new ConcurrentHashMap<>();

    public void save(ClassSession session) {
        store.put(session.getId(), session);
    }

    public ClassSession findById(String id) {
        return store.get(id);
    }
}
