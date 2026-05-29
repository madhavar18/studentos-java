package com.studentos.service;

import com.studentos.model.Task;
import com.studentos.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

// @Service tells Spring: this is a bean containing business logic
// Notice: TaskService does NOT know about HTTP at all
// It does not know if the request came from a browser, a mobile app, or a test
// That is WHY it is separate from the controller
@Service
public class TaskService {

    // Constructor injection - Spring provides the TaskRepository bean
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Business rule: when retrieving a task, calculate its urgency
    // This is business logic - it belongs in Service, not in Controller or Repository
    public Optional<Task> getTaskById(Integer id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        // Business rule: new tasks are never completed
        task.setCompleted(false);
        // Business rule: default priority if not specified
        if(task.getPriority() == null || task.getPriority().isBlank()) {
            task.setPriority("medium");
        }
        return taskRepository.save(task);
        // If task.id is null: INSERT INTO tasks (...) VALUES (...)
        // PostgreSQL assigns the id, Hibernate sets it on the returned object
    }

    public Optional<Task> completeTask(Integer id) {
        Optional<Task> found = taskRepository.findById(id);
        if(found.isPresent()) {
            Task task = found.get();
            task.setCompleted(true);
            // Business rule: update the task in the store
            taskRepository.save(task);
            return Optional.of(task);
        }
        return Optional.empty();
    }

    public List<Task> getPendingTasks() {
        return taskRepository.findByCompleted(false);
    }

    public List<Task> getOverdueTasks() {
        return taskRepository.findByDeadlineBefore(LocalDate.now())
                .stream()
                .filter(t -> !t.isCompleted())
                .toList();
    }

    public boolean deleteTask(Integer id) {
        if(taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Business logic: calculate urgency based on deadline
    // WHY this is in Service not Controller:
    // Multiple controllers might need urgency (dashboard, task list, task detail)
    // If urgency logic changes, you change one place: here
    public String calculateUrgency(Task task) {
        if(task.isCompleted()) return "none";
        long days = ChronoUnit.DAYS.between(LocalDate.now(), task.getDeadline());
        if(days < 0) return "overdue";
        if(days == 0) return "today";
        if(days <= 3) return "soon";
        return "low";
    }
}
