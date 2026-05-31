package com.studentos.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

// This class represents what the API returns to clients
// It contains computed fields that do not exist in the database
// It contains only what the client needs - nothing internal
public class TaskResponse {

    private Integer id;
    private String title;
    private String type;
    private LocalDate deadline;
    private String priority;
    private boolean completed;
    private LocalDateTime createdAt;

    // Computed fields — not in the database, calculated in the mapper
    // WHY not store these: they are always derivable from other fields
    // Storing them creates two sources of truth that can disagree
    private String urgencyLevel; // overdue, today, soon, low, none
    private int daysUntilDeadline;
    private String formattedDeadline; // "3 days remaining" or "2 days overdue"

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public int getDaysUntilDeadline() {
        return daysUntilDeadline;
    }

    public void setDaysUntilDeadline(int daysUntilDeadline) {
        this.daysUntilDeadline = daysUntilDeadline;
    }

    public String getFormattedDeadline() {
        return formattedDeadline;
    }

    public void setFormattedDeadline(String formattedDeadline) {
        this.formattedDeadline = formattedDeadline;
    }
}
