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
    public Optional<Task> getTaskByid(int id) {
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
    }

    public Optional<Task> completeTask(int id) {
        Optional<Task> found = taskRepository.findById(id);
        if(found.isPresent()) {
            Task task = found.get();
            task.setCompleted(true);
            // Business rule: update the task in the store
            taskRepository.update(id, task);
            return Optional.of(task);
        }
        return Optional.empty();
    }

    public boolean deleteTask(int id) {
        return taskRepository.deleteById(id);
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
