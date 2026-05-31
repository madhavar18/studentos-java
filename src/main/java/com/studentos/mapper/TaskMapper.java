package com.studentos.mapper;

import com.studentos.dto.CreateTaskRequest;
import com.studentos.dto.TaskResponse;
import com.studentos.model.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// @Component makes this a Spring bean - injectable anywhere
// WHY a separate mapper class:
// Conversion logic is its own concern - separate from business logic (Service)
// and from database access (Repository). If the response format changes,
// only this file changes.
@Component
public class TaskMapper {

    // Convert entity to response DTO
    public TaskResponse toResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setType(task.getType());
        response.setDeadline(task.getDeadline());
        response.setPriority(task.getPriority() != null ? task.getPriority() : "medium");
        response.setCompleted(task.isCompleted());
        response.setCreatedAt(task.getCreatedAt());

        // Compute urgency - business logic that belongs in the response layer
        long days = task.getDeadline() != null
                ? ChronoUnit.DAYS.between(LocalDate.now(), task.getDeadline())
                : 0;

        response.setDaysUntilDeadline((int) days);

        if (task.isCompleted()) {
            response.setUrgencyLevel("none");
            response.setFormattedDeadline("Completed");
        } else if (days < 0) {
            response.setUrgencyLevel("overdue");
            response.setFormattedDeadline(Math.abs(days) + " day" + (Math.abs(days) == 1 ? "" : "s") + " overdue");
        } else if (days == 0) {
            response.setUrgencyLevel("today");
            response.setFormattedDeadline("Due today");
        } else if (days <= 3) {
            response.setUrgencyLevel("soon");
            response.setFormattedDeadline(days + " day" + (days == 1 ? "" : "s") + " remaining");
        } else {
            response.setUrgencyLevel("low");
            response.setFormattedDeadline(days + " days remaining");
        }
        return response;
    }

    // Convert request DTO to entity
    public Task toEntity(CreateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle().trim());
        task.setType(request.getType());
        task.setDeadline(request.getDeadline());
        task.setPriority(
                request.getPriority() != null && !request.getPriority().isBlank()
                    ? request.getPriority() : "medium"
        );
        if (request.getNotes() != null) {
            // notes field — we will add this to Task entity in a moment
        }
        task.setCompleted(false);
        return task;
    }
}
