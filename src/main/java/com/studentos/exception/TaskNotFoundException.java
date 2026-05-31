package com.studentos.exception;

// RuntimeException: unchecked — no try/catch required at call sites
// Spring's @ControllerAdvice catches it globally
public class TaskNotFoundException extends RuntimeException {

    private final Integer taskId;

    public TaskNotFoundException(Integer taskId) {
        super("Task with id " + taskId + " not found");
        this.taskId = taskId;
    }

    public Integer getTaskId() {
        return taskId;
    }
}