package com.studentos.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

// This class represents what the client sends when creating a task
// It has NO id field - the server assigns the id
// It has NO createdAt - the server sets this
// It has NO completed - new tasks are always incomplete
// The client only provides what they actually control
public class CreateTaskRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 300, message = "Title cannot exceed 300 characters")
    // @NotBlank checks for null AND empty string AND whitespace-only
    // "  " (spaces only) fails @NotBlank but passes @NotNull
    // Always use @NotBlank for string fields you require
    private String title;

    @NotNull(message = "Task type is required")
    @Pattern(
            regexp = "Assignment|Exam|Project",
            message = "Type must be Assignment, Exam, or Project"
    )
    private String type;

    @NotNull(message = "Deadline is required")
    @FutureOrPresent(message = "Deadline cannot be in the past")
    // @FutureOrPresent: deadline must be today or a future date
    // @Future: strictly future (not today) — use @FutureOrPresent for "due today" tasks
    private LocalDate deadline;

    @Pattern(
            regexp = "low|medium|high|urgent",
            message = "Priority must be low, medium, high, or urgent"
    )
    private String priority; // optional - defaults to medium in service

    private String notes; // completely optional - no validation needed

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
