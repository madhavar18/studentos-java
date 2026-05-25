package com.studentos.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

// Plain Java class - no Spring annotations needed here yet
// On Day 25 this becomes a JPA @Entity with database connections
// Today: just a simple data container
public class Task {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    private String title;
    private String type; // Assignment, Exam, Project
    private LocalDate deadline;
    private String priority; // low, medium, high, urgent
    private boolean completed;
    private LocalDateTime createdAt;

    // Constructors
    public Task() {}

    public Task(int id, String title, String type, LocalDate deadline, String priority) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
