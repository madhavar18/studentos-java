package com.studentos.controller;

import com.studentos.dto.ApiResponse;
import com.studentos.dto.CreateTaskRequest;
import com.studentos.dto.TaskResponse;
import com.studentos.exception.TaskNotFoundException;
import com.studentos.mapper.TaskMapper;
import com.studentos.model.Task;
import com.studentos.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAllTasks() {
        List<TaskResponse> responses = taskService.getAllTasks()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable Integer id) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        // orElseThrow: if empty Optional, throw the exception
        // GlobalExceptionHandler catches it and returns 404

        return ResponseEntity.ok(ApiResponse.success(taskMapper.toResponse(task)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(
            @Valid @RequestBody CreateTaskRequest request) {
        // @Valid triggers Bean Validation on CreateTaskRequest
        // If any @NotBlank, @NotNull, @Pattern fails:
        //   Spring throws MethodArgumentNotValidException
        //   GlobalExceptionHandler catches it
        //   Returns 400 with field errors in ApiResponse.error()
        // If all valid: execution reaches this line

        Task task = taskMapper.toEntity(request);
        Task saved = taskService.createTask(task);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(taskMapper.toResponse(saved)));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<TaskResponse>> completeTask(@PathVariable Integer id) {
        Task task = taskService.completeTask(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return ResponseEntity.ok(ApiResponse.success(taskMapper.toResponse(task)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Integer id) {
        if (!taskService.deleteTask(id)) {
            throw new TaskNotFoundException(id);
        }
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getPendingTasks() {
        List<TaskResponse> responses = taskService.getPendingTasks()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getOverdueTasks() {
        List<TaskResponse> responses = taskService.getOverdueTasks()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}