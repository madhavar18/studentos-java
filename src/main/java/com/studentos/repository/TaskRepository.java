package com.studentos.repository;

import com.studentos.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// @Repository tells Spring: this is a bean that handles data access
// On Day 25 this class gets replaced by one JpaRepository interface
// Today: in-memory list simulates a database
@Repository
public class TaskRepository {

    // The "database" - stored in memory
    // Resets every time the server restarts - exactly like a real DB before you connect one
    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1; // auto-increment id - same concept as database sequence

    // Initialize with sample data so you have something to see
    public TaskRepository() {
        tasks.add(new Task(nextId++, "Complete Spring Boot assignment",
                "Assignment", java.time.LocalDate.now().plusDays(3), "high"));
        tasks.add(new Task(nextId++, "Study for DBMS exam",
                "Exam", java.time.LocalDate.now().minusDays(1), "urgent"));
        tasks.add(new Task(nextId++, "Build portfolio website",
                "Project", java.time.LocalDate.now().plusDays(7), "medium"));
    }

    public List<Task> findAll() {
        return new ArrayList<>(tasks); // return a copy - callers cannot modify internal data structure
    }

    // Optional: explicitly represents "might not exist"
    // WHY Optional instead of returning null:
    // Returning null = caller might forget to check = NullPointerException at 2am in production
    // Optional = compiler forces caller to handle the "not found" case explicitly
    public Optional<Task> findById(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
    }

    public Task save(Task task) {
        task.setId(nextId++);
        task.setCreatedAt(java.time.LocalDateTime.now());
        tasks.add(task);
        return task;
    }

    public Optional<Task> update(int id, Task updatedTask) {
        for(int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getId() == id) {
                updatedTask.setId(id);
                updatedTask.setCreatedAt(tasks.get(i).getCreatedAt());
                tasks.set(i, updatedTask);
                return Optional.of(updatedTask);
            }
        }
        return Optional.empty();
    }

    public boolean deleteById(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }
}