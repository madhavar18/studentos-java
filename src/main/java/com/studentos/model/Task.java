package com.studentos.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity // tells Hibernate: this class maps to a database table
@Table(name = "tasks")  // the table name - defaults to class name if omitted
public class Task{

    @Id  // this field is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // IDENTITY: database auto-increments the id - same as SERIAL in PostgreSQL
    // WHY IDENTITY not SEQUENCE: IDENTITY uses the database's native auto-increment
    // which is simpler and works well with PostgreSQL
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    // READ_ONLY: id appears in responses but is ignored in requests
    // Client never sends id - server/database assigns it
    private Integer id;

    @Column(nullable = false, length = 300)
    // nullable = false: database enforces NOT NULL — cannot save a task without a title
    // length = 300: VARCHAR(300) in the database
    // WHY also validate in Service: database constraints are the last line of defence
    // Service validation gives better error messages before hitting the database
    private String title;

    @Column(nullable = false, length = 50)
    private String type;   // Assignment, Exam, Project

    @Column(nullable = false)
    private LocalDate deadline;
    // Hibernate maps LocalDate to DATE column in PostgreSQL automatically

    @Column(length = 20)
    private String priority; // low, medium, high, urgent

    @Column(nullable = false)
    private boolean completed = false;
    // Default false at the Java level — redundant with Service default but explicit

    @Column(name = "created_at", updatable = false)
    // updatable = false: once created_at is set, Hibernate never updates it
    // name = "created_at": column in database uses snake_case (Java uses camelCase)
    private LocalDateTime createdAt;

    @PrePersist
    // @PrePersist: Hibernate calls this method just before saving a new entity
    // Perfect for setting createdAt automatically — you never set it manually
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    // Default constructor required by JPA - Hibernate uses it to create objects
    // when loading from the database (via reflection)
    // WHY required: when Hibernate reads a row from the DB, it creates a Task object
    // using the no-args constructor then sets fields via setters
    public Task() {}

    public Task(String title, String type, LocalDate deadline, String priority) {
        this.title = title;
        this.type = type;
        this.deadline = deadline;
        this.priority = priority;
    }

    // Getters and setters - all required by JPA

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
}
